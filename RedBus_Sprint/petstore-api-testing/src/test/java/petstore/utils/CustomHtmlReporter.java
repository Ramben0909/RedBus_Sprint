package petstore.utils;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class CustomHtmlReporter implements IReporter {

    private static final String OUTPUT_DIR  = "target/custom-report";
    private static final String OUTPUT_FILE = "TestReport.html";

    @Override
    public void generateReport(List<XmlSuite> xmlSuites,
                               List<ISuite>   suites,
                               String         outputDirectory) {

        new File(OUTPUT_DIR).mkdirs();
        File report = new File(OUTPUT_DIR, OUTPUT_FILE);

        try (PrintWriter writer = new PrintWriter(new FileWriter(report))) {
            // ── Collect results ───────────────────────────────────────────────
            List<ITestResult> passed  = new ArrayList<>();
            List<ITestResult> failed  = new ArrayList<>();
            List<ITestResult> skipped = new ArrayList<>();

            for (ISuite suite : suites) {
                for (ISuiteResult result : suite.getResults().values()) {
                    ITestContext ctx = result.getTestContext();
                    passed .addAll(ctx.getPassedTests() .getAllResults());
                    failed .addAll(ctx.getFailedTests() .getAllResults());
                    skipped.addAll(ctx.getSkippedTests().getAllResults());
                }
            }

            int total = passed.size() + failed.size() + skipped.size();
            int passRate = total > 0 ? (int) Math.round(passed.size() * 100.0 / total) : 0;

            String timestamp = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date());

            writer.println(buildHtml(passed, failed, skipped, total, passRate, timestamp));

        } catch (IOException e) {
            System.err.println("[CustomHtmlReporter] Failed to write report: " + e.getMessage());
        }

        System.out.println("[CustomHtmlReporter] Report saved → " + report.getAbsolutePath());
    }


    private String buildHtml(List<ITestResult> passed,
                             List<ITestResult> failed,
                             List<ITestResult> skipped,
                             int total, int passRate, String timestamp) {

        int p = passed.size();
        int f = failed.size();
        int s = skipped.size();

        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html><html lang='en'><head>")
          .append("<meta charset='UTF-8'>")
          .append("<meta name='viewport' content='width=device-width,initial-scale=1'>")
          .append("<title>PetStore API — Test Report</title>")
          .append("<style>").append(css()).append("</style>")
          .append("</head><body>");

        // Header
        sb.append("<div class='header'>")
          .append("<h1>🛒 PetStore API — Test Execution Report</h1>")
          .append("<p class='meta'>Generated: ").append(timestamp).append("</p>")
          .append("</div>");

        // Summary cards
        sb.append("<div class='summary'>")
          .append(card("Total",   String.valueOf(total), "card-total"))
          .append(card("Passed",  String.valueOf(p),     "card-pass"))
          .append(card("Failed",  String.valueOf(f),     "card-fail"))
          .append(card("Skipped", String.valueOf(s),     "card-skip"))
          .append(card("Pass Rate", passRate + "%",      passRate == 100 ? "card-pass" : passRate >= 70 ? "card-warn" : "card-fail"))
          .append("</div>");

        // Progress bar
        sb.append("<div class='progress-wrap'>")
          .append("<div class='progress-bar'>")
          .append("<div class='bar-pass'  style='width:").append(pct(p, total)).append("%' title='Passed: ").append(p).append("'></div>")
          .append("<div class='bar-fail'  style='width:").append(pct(f, total)).append("%' title='Failed: ").append(f).append("'></div>")
          .append("<div class='bar-skip'  style='width:").append(pct(s, total)).append("%' title='Skipped: ").append(s).append("'></div>")
          .append("</div></div>");

        // Table
        sb.append("<div class='table-wrap'>")
          .append("<h2>Test Case Results</h2>")
          .append("<table>")
          .append("<thead><tr>")
          .append("<th>#</th><th>Feature</th><th>Test Method</th>")
          .append("<th>Status</th><th>Duration (ms)</th><th>Failure Reason</th>")
          .append("</tr></thead><tbody>");

        int row = 1;
        List<ITestResult> all = new ArrayList<>();
        all.addAll(passed);
        all.addAll(failed);
        all.addAll(skipped);
        all.sort((a, b) -> {
            String fa = feature(a), fb = feature(b);
            if (!fa.equals(fb)) return fa.compareTo(fb);
            return a.getName().compareTo(b.getName());
        });

        for (ITestResult r : all) {
            String status   = statusLabel(r.getStatus());
            String css      = statusCss(r.getStatus());
            long   duration = r.getEndMillis() - r.getStartMillis();
            String reason   = failReason(r);

            sb.append("<tr class='").append(css).append("'>")
              .append("<td>").append(row++).append("</td>")
              .append("<td>").append(feature(r)).append("</td>")
              .append("<td class='method'>").append(r.getName()).append("</td>")
              .append("<td><span class='badge badge-").append(css).append("'>").append(status).append("</span></td>")
              .append("<td>").append(duration).append("</td>")
              .append("<td class='reason'>").append(reason).append("</td>")
              .append("</tr>");
        }

        sb.append("</tbody></table></div>");

        // Footer
        sb.append("<div class='footer'>PetStore API Automation Suite &nbsp;|&nbsp; RestAssured + TestNG</div>");
        sb.append("</body></html>");
        return sb.toString();
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private String card(String label, String value, String cls) {
        return "<div class='card " + cls + "'><div class='card-val'>" + value +
               "</div><div class='card-label'>" + label + "</div></div>";
    }

    private double pct(int part, int total) {
        return total == 0 ? 0 : Math.round(part * 100.0 / total);
    }

    private String feature(ITestResult r) {
        io.qameta.allure.Feature f = r.getMethod()
                .getConstructorOrMethod()
                .getMethod()
                .getDeclaringClass()
                .getAnnotation(io.qameta.allure.Feature.class);
        return f != null ? f.value() : r.getTestClass().getRealClass().getSimpleName();
    }

    private String statusLabel(int status) {
        switch (status) {
            case ITestResult.SUCCESS: return "PASSED";
            case ITestResult.FAILURE: return "FAILED";
            case ITestResult.SKIP:    return "SKIPPED";
            default:                  return "UNKNOWN";
        }
    }

    private String statusCss(int status) {
        switch (status) {
            case ITestResult.SUCCESS: return "pass";
            case ITestResult.FAILURE: return "fail";
            case ITestResult.SKIP:    return "skip";
            default:                  return "";
        }
    }

    private String failReason(ITestResult r) {
        if (r.getThrowable() == null) return "—";
        String msg = r.getThrowable().getMessage();
        if (msg == null) msg = r.getThrowable().getClass().getSimpleName();
        msg = msg.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
        if (msg.length() > 200) msg = msg.substring(0, 200) + "…";
        return "<span class='err'>" + msg + "</span>";
    }

    // ── CSS ───────────────────────────────────────────────────────────────────

    private String css() {
        return
        "*{box-sizing:border-box;margin:0;padding:0}" +
        "body{font-family:'Segoe UI',Arial,sans-serif;background:#f0f2f5;color:#222}" +

        ".header{background:linear-gradient(135deg,#1a1a2e,#16213e);color:#fff;" +
        "padding:32px 40px;border-bottom:4px solid #e94560}" +
        ".header h1{font-size:1.7rem;font-weight:700;letter-spacing:.5px}" +
        ".meta{margin-top:6px;font-size:.85rem;opacity:.7}" +

        ".summary{display:flex;gap:16px;padding:28px 40px;flex-wrap:wrap}" +
        ".card{flex:1;min-width:120px;border-radius:10px;padding:20px 16px;" +
        "text-align:center;box-shadow:0 2px 8px rgba(0,0,0,.1)}" +
        ".card-val{font-size:2rem;font-weight:700}" +
        ".card-label{font-size:.8rem;text-transform:uppercase;letter-spacing:1px;margin-top:4px;opacity:.85}" +
        ".card-total{background:#fff;border-top:4px solid #667eea}" +
        ".card-pass{background:#f0fff4;border-top:4px solid #38a169;color:#276749}" +
        ".card-fail{background:#fff5f5;border-top:4px solid #e53e3e;color:#c53030}" +
        ".card-skip{background:#fffbeb;border-top:4px solid #d69e2e;color:#975a16}" +
        ".card-warn{background:#fffbeb;border-top:4px solid #d69e2e;color:#975a16}" +

        ".progress-wrap{padding:0 40px 24px}" +
        ".progress-bar{display:flex;height:14px;border-radius:8px;overflow:hidden;background:#e2e8f0}" +
        ".bar-pass{background:#38a169;transition:width .4s}" +
        ".bar-fail{background:#e53e3e;transition:width .4s}" +
        ".bar-skip{background:#d69e2e;transition:width .4s}" +

        ".table-wrap{margin:0 40px 40px;background:#fff;border-radius:12px;" +
        "box-shadow:0 2px 12px rgba(0,0,0,.08);overflow:hidden}" +
        ".table-wrap h2{padding:20px 24px;font-size:1rem;font-weight:600;" +
        "border-bottom:1px solid #e2e8f0;background:#fafafa}" +
        "table{width:100%;border-collapse:collapse;font-size:.875rem}" +
        "thead{background:#2d3748;color:#fff}" +
        "th{padding:12px 16px;text-align:left;font-weight:600;letter-spacing:.4px;font-size:.8rem;text-transform:uppercase}" +
        "td{padding:11px 16px;border-bottom:1px solid #f0f0f0;vertical-align:top}" +
        "tr:last-child td{border-bottom:none}" +
        "tr.pass:hover{background:#f0fff4}" +
        "tr.fail{background:#fff8f8}" +
        "tr.fail:hover{background:#fff0f0}" +
        "tr.skip{background:#fffef0}" +
        ".method{font-family:monospace;font-size:.82rem;color:#2b6cb0}" +
        ".reason{font-size:.8rem;max-width:420px}" +
        ".err{color:#c53030;font-family:monospace;font-size:.78rem;word-break:break-word}" +

        ".badge{display:inline-block;padding:3px 10px;border-radius:999px;" +
        "font-size:.75rem;font-weight:700;letter-spacing:.5px}" +
        ".badge-pass{background:#c6f6d5;color:#276749}" +
        ".badge-fail{background:#fed7d7;color:#c53030}" +
        ".badge-skip{background:#fefcbf;color:#975a16}" +

        ".footer{text-align:center;padding:20px;font-size:.8rem;" +
        "color:#a0aec0;border-top:1px solid #e2e8f0;background:#fff}";
    }
}