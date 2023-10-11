import java.awt.*;
import javax.swing.*;

public class CreaturePanel extends JPanel implements Panel {
    enum CreatureLayout {
        HORIZONTAL, VERTICAL
    }

    Creature creature;
    SpritePanel spritePanel;
    StatsPanel statsPanel;
    CreatureLayout layout;

    public CreaturePanel(Creature creature, CreatureLayout layout) {
        super(new BorderLayout());
        this.creature = creature;
        this.layout = layout;
    }

    public void Draw(int width, int height) {
        setSize(width, height);
        removeAll();

        setBackground(Color.WHITE);

        int spriteWidth;
        int spriteHeight;
        int statsWidth;
        int statsHeight;

        spritePanel = new SpritePanel("./robot-idle.gif");
        spritePanel.setPreferredSize(new Dimension(100, 100));
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
        spritePanel.Draw(spriteWidth, spriteHeight);

        statsPanel = new StatsPanel(creature.statsContainer);
        statsPanel.setPreferredSize(new Dimension(100, 100));
        add(statsPanel, BorderLayout.CENTER);
        statsPanel.Draw(statsWidth, statsHeight);
    }
}