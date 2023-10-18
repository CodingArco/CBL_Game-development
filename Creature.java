class Creature {
    CodesContainer codesContainer;
    StatsContainer statsContainer;
    Environment environment;
    Actions actionsContainer;

    /**
     * Constructor.
     */
    public Creature(Actions actionsContainer,
        Environment environment) {
        this.actionsContainer = actionsContainer;
        this.environment = environment;
        this.statsContainer = new StatsContainer();
        this.codesContainer = CodesContainer.randomCodesContainer(6);
    }
}
