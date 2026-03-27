package Context;

import Pages.Trains.SearchTrainsPage;
import Pages.Trains.ResultsPage;
import Pages.Trains.FilterSortPage;

public class ScenarioContext {

    public SearchTrainsPage searchPage;
    public ResultsPage      resultsPage;
    public FilterSortPage   filterSortPage;

    public String  fromInput;
    public String  toInput;
    public boolean isFrom = false;

    // holds data for current Excel row being tested
    public String currentFromInput;
    public String currentFromSelect;
    public String currentToInput;
    public String currentToSelect;
    public String currentDate;
    public String currentInvalidDest;
}