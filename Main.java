import data.Creature;
import data.EatAction;
import data.Environment;
import data.PairAction;
import java.awt.*;
import javax.swing.*;
import panels.ButtonsPanel;
import panels.ColorRange;
import panels.ColorScheme;
import panels.ProgressBarPanel;
import panels.Range;
import panels.WorldPanel;

/**
 * Main class of CBL game.
 */
class Main {
    static Main instance;

    data.World world;
    Environment environment = new Environment();

    ProgressBarPanel codesPanel;
    WorldPanel worldPanel;
    ButtonsPanel buttonsPanel;
    ProgressBarPanel environmentPanel;

    ColorScheme codesColorScheme = new ColorScheme(new ColorRange[] {
        new ColorRange(0, 100, Color.ORANGE)
    }, Color.WHITE);
    ColorScheme statsColorScheme = new ColorScheme(new ColorRange[] {
        new ColorRange(0, 19, Color.RED),
        new ColorRange(20, 39, Color.ORANGE),
        new ColorRange(40, 89, Color.YELLOW),
        new ColorRange(90, 100, Color.GREEN)
    }, Color.WHITE);
    ColorScheme environmentStatsColorScheme = new ColorScheme(new ColorRange[] {
        new ColorRange(new Range(0, 19), new Color(0, 0, 255)),
        new ColorRange(new Range(20, 39), new Color(100, 0, 200)),
        new ColorRange(new Range(40, 59), new Color(150, 0, 150)),
        new ColorRange(new Range(60, 79), new Color(200, 0, 100)),
        new ColorRange(new Range(80, 100), new Color(255, 0, 0))
    }, Color.WHITE);

    /**
     * Creates a player creature.
     * 
     * @return The player creature
     */
    data.Creature createPlayerCreature() {
        return new data.Creature(
            new data.Actions(new data.Action[] {
                new EatAction(this::updateScreen),
                new PairAction(this::updateScreen)
            }),
            environment);
    }

    /**
     * Creates a world creature.
     * 
     * @return The world creature
     */
    data.Creature createWorldCreature() {
        return new data.Creature(
            new data.Actions(new data.Action[] {
                new EatAction(this::updateScreen),
                new PairAction(this::updateScreen)
            }),
            environment);
    }

    /**
     * Sets up the world with creatures.
     */
    void setupWorld() {
        data.Creature playerCreature = createPlayerCreature();
        data.Creature[] worldCreatures = new data.Creature[] {
            createWorldCreature(), createWorldCreature(), createWorldCreature(),
            createWorldCreature(), createWorldCreature()
        };

        world = new data.World(playerCreature, worldCreatures,
            this::checkIfWon);
    }

    /**
     * Sets up the screen with the frames.
     */
    void setupScreen() {
        SwingUtilities.invokeLater(() -> {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int screenWidth = (int) screenSize.getWidth();
            int screenHeight = (int) screenSize.getHeight();

            JFrame screenFrame = new JFrame();
            screenFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            screenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            screenFrame.setUndecorated(true);

            int buttonsPanelHeight = screenHeight / 10;
            buttonsPanel = new ButtonsPanel(world.getPlayerCreature());
            screenFrame.add(buttonsPanel, BorderLayout.SOUTH);
            buttonsPanel.draw(screenWidth, buttonsPanelHeight);

            int rightPanelWidth = screenWidth / 5;
            JPanel rightPanel = new JPanel();
            rightPanel.setLayout(new BorderLayout());

            JButton closeGameButton = new JButton("Close game");
            closeGameButton.addActionListener(e -> System.exit(0));
            closeGameButton.setPreferredSize(new Dimension(rightPanelWidth,
                buttonsPanelHeight));
            rightPanel.add(closeGameButton, BorderLayout.NORTH);

            int environmentPanelHeight = screenHeight / 2
                - buttonsPanelHeight / 2 - buttonsPanelHeight;
            environmentPanel = new ProgressBarPanel(
                environment,
                environmentStatsColorScheme, Color.WHITE);
            rightPanel.add(environmentPanel, BorderLayout.CENTER);
            environmentPanel.draw(rightPanelWidth, environmentPanelHeight);

            int codesPanelHeight = screenHeight / 2 - buttonsPanelHeight / 2;
            codesPanel = new ProgressBarPanel(
                world.getPlayerCreature().getCodesContainer(),
                codesColorScheme, Color.WHITE);
            rightPanel.add(codesPanel, BorderLayout.SOUTH);
            codesPanel.draw(rightPanelWidth, codesPanelHeight);

            screenFrame.add(rightPanel, BorderLayout.EAST);

            worldPanel = new WorldPanel(world, statsColorScheme,
                this::updateScreen);
            screenFrame.add(worldPanel, BorderLayout.CENTER);
            worldPanel.draw(screenWidth - rightPanelWidth,
                screenHeight - buttonsPanelHeight);

            screenFrame.setVisible(true);
        });
    }

    /**
     * Updates the screen after changes in the game data.
     */
    void updateScreen() {
        codesPanel.update();
        worldPanel.update();
        environmentPanel.update();
        buttonsPanel.update();
        checkIfLost();
    }

    /**
     * Redraws the world. Only used after UI-breaking changes in the data.
     */
    void redrawWorld() {
        Dimension dimension = worldPanel.getPreferredSize();
        int width = (int) dimension.getWidth();
        int height = (int) dimension.getHeight();
        worldPanel.draw(width, height);
    }

    /**
     * Checks if the player won the game and shows a message.
     */
    public void checkIfWon() {
        for (Creature creature : world.getWorldCreatures()) {
            if (!creature.isDead()) {
                return;
            }
        }
        updateScreen();
        JOptionPane.showMessageDialog(null, "You killed all other robots",
            "You won!",
            JOptionPane.PLAIN_MESSAGE);
        System.exit(0);
    }

    /**
     * Checks if the player lost the game and shows a message.
     */
    public void checkIfLost() {
        if (world.getPlayerCreature().isDead()) {
            JOptionPane.showMessageDialog(null,
                "You died because you ran out of energy",
                "You lost!",
                JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        instance = new Main();
        instance.setupWorld();
        instance.setupScreen();
    }
}
