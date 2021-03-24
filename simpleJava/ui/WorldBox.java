/**
 * 
 */
package golledge.empire.ui;

import golledge.empire.game.World;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Date;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 * @author Chris Golledge
 * 
 */
public class WorldBox extends JPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static int SIZE;
    javax.swing.JTextArea wInfo;
    private World world;

    public WorldBox(World world)
    {
        this.world = world;
        this.setBorder(world.getOwner().getColor());
        this.setBackground(Color.black);
        // Galaxy layout manager overrides, but leaving in case switch layout
        this.setBounds(SIZE * world.getX(), SIZE * world.getY(), SIZE, SIZE);
        this.setLayout(new BorderLayout(0, 0));

        wInfo = new JTextArea();
        this.add(wInfo);
        wInfo.setBackground(Color.BLACK);
        wInfo.setEditable(false);
        wInfo.setFont(new Font("Monospaced", Font.BOLD, 14));

        this.refresh();
    }

    private static int BLINK_TIME = 500;
    java.util.Timer blinker;

    class BlinkerTask extends TimerTask
    {
        private Color alt;
        private boolean flip = true;

        public void setColor(Color alt)
        {
            this.alt = alt;
        };

        public void run()
        {
            if (flip)
            {
                setBorder(alt);
                flip = false;
            }
            else
            {
                setBorder(world.getOwner().getColor());
                flip = true;
            }
            paintImmediately(getVisibleRect());
        }
    }

    public void startBlinking(final Color fleetColor)
    {
        blinker = new java.util.Timer();
        BlinkerTask task = new BlinkerTask();
        task.setColor(fleetColor.brighter());
        blinker.scheduleAtFixedRate(task, new Date(), BLINK_TIME);
    }

    public void stopBlinking()
    {
        if (blinker != null)
            blinker.cancel();
        blinker = null;
    }

    public void refresh()
    {
        stopBlinking();
        wInfo.setText(world.toString());
        setBorder(world.getOwner().getColor());
        setColor(world.getOwner().getColor());
        paintImmediately(getVisibleRect());
    }

    void setBorder(Color base)
    {
        this.setBorder(new SoftBevelBorder(BevelBorder.RAISED, base.brighter(),
                base.brighter().brighter(), base.darker(), base.darker()
                        .darker()));
    }

    void setColor(Color base)
    {
        wInfo.setForeground(base);
        this.setBorder(new SoftBevelBorder(BevelBorder.RAISED, base.brighter(),
                base.brighter().brighter(), base.darker(), base.darker()
                        .darker()));
    }
}
