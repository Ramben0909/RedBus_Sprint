package Context;

import Pages.Trains.SearchTrainsPage;
import Pages.Trains.ResultsPage;
import Pages.Trains.FilterSortPage;

public class ScenarioContext {

    // ── ThreadLocal storage ───────────────────────────────────────────
    private static final ThreadLocal<ScenarioContext> INSTANCE =
        ThreadLocal.withInitial(ScenarioContext::new);

    public static ScenarioContext get() {
        return INSTANCE.get();
    }

    public static void reset() {
        INSTANCE.remove();
    }

    // ── your existing fields — unchanged ─────────────────────────────
    public SearchTrainsPage searchPage;
    public ResultsPage      resultsPage;
    public FilterSortPage   filterSortPage;

    public String  fromInput;
    public String  toInput;
    public boolean isFrom = false;

    public String currentFromInput;
    public String currentFromSelect;
    public String currentToInput;
    public String currentToSelect;
    public String currentDate;
    public String currentInvalidDest;
}