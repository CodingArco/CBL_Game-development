import java.awt.*;
import javax.swing.*;

class CreaturePanel extends JPanel implements Panel {
    enum CreatureLayout {
        HORIZONTAL, VERTICAL
    }

    Creature creature;
    SpritePanel spritePanel;
    StatsPanel statsPanel;
    CreatureLayout layout;

    /**
     * Constructor.
     * @param creature Creature to show
     * @param layout Layout of the panel
     */
    public CreaturePanel(Creature creature, CreatureLayout layout) {
        super(new BorderLayout());
        this.creature = creature;
        this.layout = layout;
    }

    /**
     * Draws the sprite and stats.
     * @param width Width of the panel in pixels
     * @param height Height of the panel in pixels
     */
    public void draw(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        removeAll();

        setBackground(Color.WHITE);

        int spriteWidth;
        int spriteHeight;
        int statsWidth;
        int statsHeight;

        spritePanel = new SpritePanel("./robot-idle.gif");
        if (layout == CreatureLayout.HORIZONTAL) {
            add(spritePanel, BorderLayout.EAST);
            spriteWidth = height;
            spriteHeight = height;
            statsWidth = width - spriteWidth;
            statsHeight = height;
        } else {
            add(spritePanel, BorderLayout.NORTH);
            spriteWidth = width;
            spriteHeight = width;
            statsWidth = width;
            statsHeight = height - spriteHeight;
        }
        spritePanel.draw(spriteWidth, spriteHeight);

        statsPanel = new StatsPanel(creature.statsContainer);
        add(statsPanel, BorderLayout.CENTER);
        statsPanel.draw(statsWidth, statsHeight);
    }
}
