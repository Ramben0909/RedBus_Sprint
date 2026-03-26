package Context;

import Pages.Trains.SearchTrainsPage;
import Pages.Trains.ResultsPage;
import Pages.Trains.FilterSortPage;

public class ScenarioContext {
	public SearchTrainsPage searchPage;
	public ResultsPage resultsPage;
	public FilterSortPage filterSortPage;

    public String fromInput;
    public String toInput;
    public boolean isFrom = false;
}