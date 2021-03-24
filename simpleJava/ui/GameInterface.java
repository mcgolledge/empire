/*******************************************************************************
 * Copyright 2013 M. Chris Golledge
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package golledge.empire.ui;

import golledge.empire.game.Fleet;
import golledge.empire.game.Game;
import golledge.empire.game.Player;
import golledge.empire.game.World;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import org.eclipse.wb.swing.FocusTraversalOnArray;

public class GameInterface extends JFrame
{
    public static final String FONTNAME = "Trebuchet MS";
    private static final Font FONTPLAIN14 = new Font(FONTNAME, Font.PLAIN, 14);
    private final Color defForeground = Color.lightGray;
    private final Color defBackground = Color.black;

    private static final long serialVersionUID = 1L;
    private Game game;
    private JPanel contentPane;

    JPanel galaxy;
    World filler;
    Hashtable<String, WorldBox> worldBoxes;

    private JTextField fromWorld;
    private JTextField toWorld;
    private JTextField travelTime;
    private JTextField fleetSize;

    private JTextField fromLbl;
    private JTextField toLbl;
    private JTextField travelTimeLbl;
    private JTextField sizeLbl;

    private JTextField modeTitle;
    private JLabel curYear;
    private JPanel fleetPanel;

    private JButton btnEndTurn;

    public Component getConfirmArea()
    {
        return btnEndTurn;
    }

    Teletype status;

    public static int screenX;
    public static int screenY;

    public static enum Mode
    {
        FLEET_ENTRY, REINFORCEMENTS, BATTLE
    }

    /**
     * Create the frame.
     */
    public GameInterface()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit defTK = Toolkit.getDefaultToolkit();

        // get the screen size
        Dimension screenSize = defTK.getScreenSize();
        screenX = screenSize.width;
        screenY = screenSize.height;

        setFont(new Font(FONTNAME, Font.PLAIN, 12));
        setTitle("Empire");
        setIconImage(defTK
                .getImage(GameInterface.class
                        .getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
        setBounds(0, 0, 1211, 1080);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(16, 0, 45));
        contentPane.setBorder(UIManager.getBorder("CheckBoxMenuItem.border"));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]
        { 320, 180 };
        gbl_contentPane.rowHeights = new int[]
        { 25, 122, 227, 50 };
        gbl_contentPane.columnWeights = null; // new double[] { 0.0, 0.0, 0.0 };
        gbl_contentPane.rowWeights = null; // new double[]{ 0.0, 0.0, 0.0 };
        contentPane.setLayout(gbl_contentPane);

        modeTitle = new JTextField("Current Player or Game State");
        modeTitle.setEditable(false);
        // currentPlayer.setLabelFor(contentPane);
        modeTitle.setHorizontalAlignment(SwingConstants.CENTER);
        modeTitle.setBackground(contentPane.getBackground());
        modeTitle.setForeground(Player.neutral.getColor());
        modeTitle.setFont(new Font(FONTNAME, Font.BOLD, 22));
        modeTitle.setBorder(BorderFactory.createEmptyBorder());
        modeTitle.setMinimumSize(getPreferredSize());
        GridBagConstraints gbc_currentPlayer = new GridBagConstraints();
        gbc_currentPlayer.insets = new Insets(0, 0, 5, 5);
        // gbc_currentPlayer.insets = new Insets(1, 1, 5, 5);
        gbc_currentPlayer.gridx = 0;
        gbc_currentPlayer.gridy = 0;
        contentPane.add(modeTitle, gbc_currentPlayer);

        curYear = new JLabel("Year:  0");
        curYear.setVerticalAlignment(SwingConstants.TOP);
        curYear.setForeground(Color.WHITE);
        curYear.setHorizontalAlignment(SwingConstants.LEFT);
        curYear.setFont(new Font(FONTNAME, Font.BOLD, 14));
        GridBagConstraints gbc_curYear = new GridBagConstraints();
        gbc_curYear.insets = new Insets(0, 0, 5, 0);
        gbc_curYear.anchor = GridBagConstraints.NORTHEAST;
        // gbc_curYear.insets = new Insets(0, 0, 5, 0);
        gbc_curYear.gridx = 1;
        gbc_curYear.gridy = 0;
        contentPane.add(curYear, gbc_curYear);

        fleetPanel = new JPanel();
        fleetPanel.setForeground(defForeground);
        fleetPanel.setBackground(defBackground);
        fleetPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        fleetPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        fleetPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null,
                null, null, null));
        GridBagConstraints gbc_fleetPanel = new GridBagConstraints();
        gbc_fleetPanel.anchor = GridBagConstraints.NORTHWEST;
        gbc_fleetPanel.insets = new Insets(0, 0, 5, 0);
        gbc_fleetPanel.gridx = 1;
        gbc_fleetPanel.gridy = 1;
        contentPane.add(fleetPanel, gbc_fleetPanel);
        fleetPanel.setLayout(new GridLayout(4, 4, 0, 0));

        fromLbl = new JTextField("Send ships from:");
        fromLbl.setBackground(defBackground);
        fromLbl.setForeground(defForeground);
        fromLbl.setEditable(false);
        fromLbl.setFont(FONTPLAIN14);
        fleetPanel.add(fromLbl);

        fromWorld = new JTextField();
        fromWorld.setFont(FONTPLAIN14);
        fleetPanel.add(fromWorld);
        fromWorld.setColumns(10);

        toLbl = new JTextField("Send ships to:");
        toLbl.setForeground(defForeground);
        toLbl.setBackground(defBackground);
        toLbl.setEditable(false);
        toLbl.setFont(FONTPLAIN14);
        fleetPanel.add(toLbl);

        toWorld = new JTextField();
        toWorld.setFont(FONTPLAIN14);
        fleetPanel.add(toWorld);
        toWorld.setColumns(10);

        sizeLbl = new JTextField("Size of Fleet:");
        sizeLbl.setBackground(defBackground);
        sizeLbl.setForeground(defForeground);
        sizeLbl.setEditable(false);
        sizeLbl.setFont(FONTPLAIN14);
        fleetPanel.add(sizeLbl);
        // NumberFormatter shipsFormatter = new NumberFormatter(
        // java.text.NumberFormat.getIntegerInstance());
        // shipsFormatter.setMinimum(1);
        // fleetSize = new JFormattedTextField(shipsFormatter);
        fleetSize = new JTextField();
        fleetSize.setFont(FONTPLAIN14);
        fleetPanel.add(fleetSize);
        fleetSize.setColumns(10);
        fleetSize.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                int nShips = 0;
                boolean isOrdinal = false;
                try
                {
                    nShips = Integer.parseInt(fleetSize.getText());
                    isOrdinal = nShips > 0;
                }
                catch (NumberFormatException nfe)
                {
                }
                if (isOrdinal == false)
                {
                    announce("The value \"" + fleetSize.getText()
                            + "\" is not a positive integer.",
                            Teletype.MessageType.WARNING);
                    fleetSize.setText("");
                    return;
                }

                if (game.getCurFromWorld().getShips() >= nShips)
                {
                    game.addFleet(nShips);
                }
                else
                {
                    announce("Insufficient ships!",
                            Teletype.MessageType.WARNING);
                }
                clearFields();
            }
        });

        travelTimeLbl = new JTextField("Travel Time:");
        travelTimeLbl.setForeground(defForeground);
        travelTimeLbl.setBackground(defBackground);
        travelTimeLbl.setEditable(false);
        travelTimeLbl.setFont(FONTPLAIN14);
        fleetPanel.add(travelTimeLbl);

        travelTime = new JTextField();
        travelTime.setFont(FONTPLAIN14);
        travelTime.setEditable(false);
        fleetPanel.add(travelTime);
        travelTime.setColumns(10);
        fleetPanel.setFocusTraversalPolicy(new FocusTraversalOnArray(
                new Component[]
                { fromWorld, toWorld, fleetSize, travelTime }));

        addFromListener();
        addToListener();

        galaxy = new JPanel();
        galaxy.setAlignmentY(Component.TOP_ALIGNMENT);
        galaxy.setAlignmentX(Component.LEFT_ALIGNMENT);
        galaxy.setAutoscrolls(true);
        galaxy.setBackground(Color.black);
        galaxy.setBounds(0, 0, getHeight() - 120, getHeight() - 120);
        WorldBox.SIZE = galaxy.getWidth() / 20;

        // Have not figured out a good grid style layout that
        // does not collapse inward from a 20x20 if it is not full.
        // Filler are world objects used to keep the grid spaced out.
        // Potential improvement area.
        filler = new World();
        filler.setOwner(new Player("nil", galaxy.getBackground()));

        GridBagConstraints gbc_galaxy = new GridBagConstraints();
        gbc_galaxy.gridheight = 3;
        gbc_galaxy.anchor = GridBagConstraints.SOUTHWEST;
        gbc_galaxy.insets = new Insets(0, 0, 5, 5);
        gbc_galaxy.gridy = 1;
        gbc_galaxy.gridx = 0;
        contentPane.add(galaxy, gbc_galaxy);
        galaxy.setLayout(new GridLayout(20, 20, 0, 0));

        status = new Teletype();
        status.setLineWrap(true);
        status.setWrapStyleWord(true);
        status.setColumns(30);
        status.setRows(4);
        status.setEditable(false);
        status.setFont(FONTPLAIN14);
        status.setText("Status message area.\nConfirmation of fleet launch\nand fleet arrival messages.");
        status.setForeground(Player.neutral.getColor());
        status.setBackground(contentPane.getBackground());
        status.setBorder(null);
        GridBagConstraints gbc_statusMessage = new GridBagConstraints();
        gbc_statusMessage.insets = new Insets(0, 0, 5, 0);
        gbc_statusMessage.anchor = GridBagConstraints.NORTHWEST;
        gbc_statusMessage.gridx = 1;
        gbc_statusMessage.gridy = 2;
        contentPane.add(status, gbc_statusMessage);

        btnEndTurn = new JButton("End Turn");
        btnEndTurn.setAlignmentY(Component.TOP_ALIGNMENT);
        btnEndTurn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        btnEndTurn.setFont(new Font(FONTNAME, Font.PLAIN, 12));
        btnEndTurn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                game.endTurn();
            }
        });
        GridBagConstraints gbc_btnEndTurn = new GridBagConstraints();
        gbc_btnEndTurn.anchor = GridBagConstraints.NORTHEAST;
        gbc_btnEndTurn.gridx = 1;
        gbc_btnEndTurn.gridy = 3;
        contentPane.add(btnEndTurn, gbc_btnEndTurn);

        this.setLocationRelativeTo(null);
        this.setBounds((screenX - 1200) / 2, (screenY - 1000) / 2, 1200, 1000);
    }

    public Game getGame()
    {
        return game;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }

    void fillTestGrid()
    {
        Hashtable<String, World> worlds = new Hashtable<String, World>();

        for (int x = 0; x <= Game.X_MAX; x++)
            for (int y = 0; y <= Game.Y_MAX; y++)
            {
                filler = new World();
                filler.setX(x);
                filler.setY(y);
                filler.setOwner(Player.neutral);
                filler.setName(String.valueOf((char) ((int) 'a' + (x)))
                        + String.valueOf((char) ((int) 'a' + (y))));
                filler.setKnown(true);
                filler.setProduction(x % 11);
                filler.setShips(x * y);

                worlds.put(filler.getName(), filler);
            }

        makeMap(worlds);
    }

    public void makeMap(Hashtable<String, World> worlds)
    {
        galaxy.removeAll();
        worldBoxes = new Hashtable<String, WorldBox>();

        // Horribly inefficient, but need to fill the grid box
        // galaxy with filler or it collapses.
        // TODO: Improve this. Layout manager that does not collapse or other.
        WorldBox wb;
        Enumeration<World> eWorlds;
        for (int x = 0; x <= Game.X_MAX; x++)
            for (int y = 0; y <= Game.Y_MAX; y++)
            {
                wb = null;
                eWorlds = worlds.elements();
                while (eWorlds.hasMoreElements())
                {
                    World w = eWorlds.nextElement();
                    if (w.getX() == x && w.getY() == y)
                    {
                        wb = new WorldBox(w);
                        worldBoxes.put(w.getName(), wb);
                    }

                }
                if (wb == null)
                    wb = new WorldBox(filler);
                galaxy.add(wb);
            }

        galaxy.invalidate();
        galaxy.validate();
        fromWorld.requestFocusInWindow();
    }

    /**
     * Sets the events listened to for the txtFromWorld field.
     * 
     * @param tf
     */
    void addFromListener()
    {
        KeyListener keyListener = new KeyListener()
        {
            public void keyPressed(KeyEvent keyEvent)
            {
            }

            public void keyReleased(KeyEvent keyEvent)
            {
            }

            public void keyTyped(KeyEvent keyEvent)
            {
                fromWorld.setText(""); // clear this field so that only this
                                       // last
                                       // key is shown
                toWorld.setText("");
                fleetSize.setText("");
                char keyChar = keyEvent.getKeyChar();
                if (Character.isLetter(keyChar))
                {
                    String keyStr = String.valueOf(keyChar);
                    World w = game.getWorlds().get(keyStr);
                    if (w != null)
                    {
                        game.setCurFromWorld(w);
                        toWorld.requestFocusInWindow();
                    }
                    else
                    {
                        fromWorld.requestFocusInWindow();
                        clearFields();
                        return;
                    }

                    if (game.getCurPlayer() == game.getCurFromWorld()
                            .getOwner())
                    {
                        fleetSize.setEnabled(true);
                    }
                    else
                    {
                        fleetSize.setEnabled(false);
                    }
                    toWorld.requestFocusInWindow();
                }
                else
                {
                    fromWorld.requestFocusInWindow();
                }
            }
        };
        fromWorld.addKeyListener(keyListener);
    }

    /**
     * Sets the events listened to for the txtFromWorld field.
     * 
     * @param tf
     */
    void addToListener()
    {
        KeyListener keyListener = new KeyListener()
        {
            public void keyPressed(KeyEvent keyEvent)
            {
            }

            public void keyReleased(KeyEvent keyEvent)
            {
            }

            public void keyTyped(KeyEvent keyEvent)
            {
                toWorld.setText("");
                fleetSize.setText("");
                char keyChar = keyEvent.getKeyChar();
                if (Character.isLetter(keyChar))
                {
                    String w = String.valueOf(keyChar);
                    World toWorld = game.getWorlds().get(w);
                    game.setCurToWorld(toWorld);
                    travelTime.setText(String.valueOf(Game.delta_t(
                            game.getCurFromWorld(), toWorld)));
                    int arrivalYear = game.getCurYear()
                            + Game.delta_t(game.getCurToWorld(),
                                    game.getCurFromWorld());

                    if (arrivalYear <= game.getLastYear() + 1)
                    {
                        if (fleetSize.isEnabled())
                            fleetSize.requestFocusInWindow();
                        else
                            fromWorld.requestFocusInWindow();
                    }
                    else
                    {
                        clearFields();
                        announce(
                                "Fleet would arrive after the end of the game.",
                                Teletype.MessageType.WARNING);
                        fromWorld.requestFocusInWindow();
                    }
                }
                else
                {
                    toWorld.requestFocusInWindow();
                }
            }
        };
        toWorld.addKeyListener(keyListener);
    }

    public void setPlayer(Player curPlayer)
    {
        modeTitle.setText("Admiral " + curPlayer.getName());
        modeTitle.setForeground(curPlayer.getColor());
        // modeTitle.computeVisibleRect(getMaximizedBounds());
        // modeTitle.getParent().revalidate();
        modeTitle.paintImmediately(modeTitle.getVisibleRect());
    }

    public void setState(String state)
    {
        // TextSetter cf = new TextSetter((JTextComponent)currentPlayer, state);
        // cf.start();
        // sleepSafe(delay);

        modeTitle.setText(state);
        modeTitle.setForeground(defForeground);
        modeTitle.paintImmediately(modeTitle.getVisibleRect());
    }

    public void setYear(int curYear)
    {
        this.curYear.setText("Year:  " + String.valueOf(curYear));
        this.curYear.paintImmediately(this.curYear.getVisibleRect());
    }

    public void setMode(Mode ala, Fleet f)
    {
        switch (ala)
        {
        case FLEET_ENTRY:
            configFleetEntry();
            break;
        case BATTLE:
            configBattle(f);
            break;
        case REINFORCEMENTS:
            configReinforcements(f);
            break;
        }
        clearFields();
    }

    private void configFleetEntry()
    {
        fromLbl.setForeground(defForeground);
        fromLbl.setText("Send ships from:");
        fromWorld.setEditable(true);
        fromWorld.setEnabled(true);

        toLbl.setForeground(defForeground);
        toLbl.setText("Send ships to:");
        toWorld.setEditable(true);
        toWorld.setEnabled(true);

        sizeLbl.setForeground(defForeground);
        sizeLbl.setText("Size of Fleet:");
        fleetSize.setEditable(true);
        fleetSize.setEnabled(true);

        travelTimeLbl.setForeground(defForeground);
        travelTimeLbl.setText("Travel Time:");
        travelTime.setEditable(false);
        fromWorld.requestFocusInWindow();
    }

    private void configBattle(Fleet f)
    {
        worldBoxes.get(f.getDestination().getName()).startBlinking(
                f.getOwner().getColor());

        fromWorld.setEditable(false);
        fromLbl.setForeground(f.getOwner().getColor());
        fromLbl.setText(f.getOwner().getName());
        fromLbl.paintImmediately(fromLbl.getVisibleRect());
        toLbl.setForeground(f.getDestination().getOwner().getColor());
        toLbl.setText(f.getDestination().getOwner().getName());
        toWorld.setEditable(false);
        toLbl.paintImmediately(toLbl.getVisibleRect());
    }

    private void configReinforcements(Fleet f)
    {
        worldBoxes.get(f.getDestination().getName()).startBlinking(
                f.getOwner().getColor().darker());

        fromWorld.setEditable(false);
        fromLbl.setForeground(f.getOwner().getColor());
        fromLbl.setText(f.getOwner().getName());
        fromLbl.paintImmediately(fromLbl.getVisibleRect());
        toLbl.setForeground(f.getDestination().getOwner().getColor());
        toLbl.setText(f.getDestination().getOwner().getName());
        toWorld.setEditable(false);
        toLbl.paintImmediately(toLbl.getVisibleRect());
    }

    public void clearFields()
    {
        fromWorld.setText("");
        toWorld.setText("");
        travelTime.setText("");
        fleetSize.setText("");
        this.status.setText("");

        fromWorld.requestFocusInWindow();
    }

    private void pause(int msec)
    {
        try
        {
            Thread.sleep(msec);
        }
        catch (InterruptedException e)
        {
        }
    }

    public void announceReinforcements(Fleet f)
    {
        World dest = f.getDestination();
        String name = dest.getName();
        WorldBox box = worldBoxes.get(name);
        announce("World " + name + ":  " + String.valueOf(f.getShips())
                + " reinforcements arriving.", f.getOwner().getColor());
    }

    public void announceBattle(Fleet f)
    {
        World dest = f.getDestination();
        String name = dest.getName();
        announce("World " + name + ":  " + String.valueOf(f.getShips())
                + " attackers arriving.", f.getOwner().getColor(), false);
    }

    public void announce(String msg)
    {
        announce(msg, Teletype.MessageType.INFORMATION);
    }

    public void announce(String msg, Teletype.MessageType mt)
    {
        boolean clear;
        Color color;
        switch (mt)
        {
        case BATTLE:
            color = Color.RED;
            clear = false;
            break;
        case INFORMATION:
            color = Color.GREEN;
            clear = true;
            break;
        case WARNING:
            color = Color.YELLOW;
            clear = true;
            break;
        default:
            color = Color.GREEN;
            clear = true;
        }

        status.setText(msg, color, clear);
    }

    public void announce(String msg, Color color)
    {
        status.setText(msg, color, true);
    }

    public void announce(String msg, Color color, boolean autoClear)
    {
        status.setText(msg, color, autoClear);
    }

    public void refreshWorlds()
    {
        Enumeration<WorldBox> iwb = worldBoxes.elements();

        while (iwb.hasMoreElements())
        {
            iwb.nextElement().refresh();
        }
    }

    public void refresh(World w)
    {
        worldBoxes.get(w.getName()).refresh();
    }

    public void updateShipCounts(int pillagers, int villagers)
    {
        fromWorld.setText(String.valueOf(pillagers));
        fromWorld.paintImmediately(fromWorld.getVisibleRect());
        toWorld.setText(String.valueOf(villagers));
        toWorld.paintImmediately(toWorld.getVisibleRect());
        pause(villagers, pillagers);

    }

    /**
     * Calculates the amount of delay between ship count updates during battle.
     * 
     * @param villagers
     * @param pillagers
     */
    private void pause(int villagers, int pillagers)
    {
        int delay = 2000 / (4 + Math.min(villagers, pillagers));
        pause(delay);
    }

    public void announcePillagerVictory(Fleet fleet)
    {
        setPlayer(fleet.getOwner());
        announce("Admiral " + fleet.getOwner()
                + " has taken control of world '"
                + fleet.getDestination().getName() + "'.", fleet.getOwner()
                .getColor());

    }

    public void announceVillagerVictory(Fleet fleet)
    {
        announce("Admiral " + fleet.getDestination().getOwner()
                + " has fought back the invaders on world '"
                + fleet.getDestination().getName() + "'.", fleet
                .getDestination().getOwner().getColor());

    }

    public int confirmMap()
    {
        return JOptionPane.showConfirmDialog(getConfirmArea(),
                "Do you want to play this map?\n"
                        + "Pressing 'No' will create another map.",
                "Map Confirmation", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    }

    public void okContinue(String title, String msg)
    {
        JOptionPane.showMessageDialog(getConfirmArea(), msg, title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void disableInput()
    {
        fromWorld.setEnabled(false);
        toWorld.setEnabled(false);
        travelTime.setEnabled(false);
        fleetSize.setEnabled(false);
        btnEndTurn.setEnabled(false);
    }

    /**
     * Interface test.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    GameInterface frame = new GameInterface();
                    frame.fillTestGrid();
                    frame.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

}
