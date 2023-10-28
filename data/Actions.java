package data;

/**
 * Actions container with actions.
 */
public class Actions implements interfaces.Actions {
    public static Creature selectedCreature;
    public static Creature actionCreature;

    Creature creature;

    Action[] actions;

    public Actions(Action[] actions) {
        this.actions = actions;
    }

    /**
     * Set the creature for the actions to be executed on.
     * 
     * @param creature Creature to execute actions on
     */
    void setActionsCreature(Creature creature) {
        this.creature = creature;
        for (Action action : actions) {
            action.setCreature(creature);
        }
    }

    /**
     * Get an action by its class.
     * 
     * @param <T> Action class type
     * @param actionClass Action class
     * @return The action
     */
    public <T extends Action> Action getAction(Class<T> actionClass) {
        for (Action action : actions) {
            if (action.getClass().equals(actionClass)) {
                return action;
            }
        }
        return null;
    }

    @Override
    public Action[] getActions() {
        return actions;
    }

    @Override
    public void selectCreature() {
        selectedCreature = this.creature;
    }

    @Override
    public boolean isSelectedCreature() {
        return selectedCreature == this.creature;
    }

    @Override
    public void selectActionCreature() {
        actionCreature = creature;
    }

    @Override
    public boolean isActionCreature() {
        return actionCreature == this.creature;
    }
}
