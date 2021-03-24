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

import golledge.empire.game.Game;
import golledge.empire.game.GameSettings;
import golledge.empire.game.Player;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.text.NumberFormatter;

import org.eclipse.wb.swing.FocusTraversalOnArray;

public class SettingsUI extends JDialog
{
    private static final Font FONT11 = new Font(GameInterface.FONTNAME,
            Font.BOLD, 11);

    private static final long serialVersionUID = 1L; // for serializable

    private Panel contentPane;
    JPanel playerPanel;
    private JTextField blueName;
    private JTextField redName;
    private JTextField ltPurpleName;
    private JTextField lightBlueName;
    private JTextField lightGreenName;
    private JTextField brownName;
    private JTextField orangeName;
    private JTextField greenName;
    private JTextField purpleName;
    private JTextField yellowName;

    private JFormattedTextField neutralProductionTF;
    JSlider neutralProduction;

    private JFormattedTextField nWorldsTF = null;
    private JSlider nWorlds;
    private JLabel nWorldsLabel = null;

    private JFormattedTextField neutralShipsInitTF;
    private JSlider neutralShipsInit;

    private JFormattedTextField lastYearTF;

    TextWindow instructW;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    SettingsUI frame = new SettingsUI(null);
                    frame.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public SettingsUI(final Game game)
    {
        setModal(true);
        final SettingsUI this_this = this; // used in sub-type methods

        // setIconImage(Toolkit.getDefaultToolkit().getImage(SettingsUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/question.png")));
        setTitle("Empire Setup");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 759, 590);
        contentPane = new Panel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblSetupOptions = new JLabel("Setup Options");
        lblSetupOptions
                .setFont(new Font(GameInterface.FONTNAME, Font.BOLD, 16));
        lblSetupOptions.setBounds(217, 11, 128, 28);
        contentPane.add(lblSetupOptions);

        // Player panel
        playerPanel = new JPanel();
        playerPanel
                .setToolTipText("Any non-blank field will be considered an active player's name.");
        playerPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null,
                null, null, null));
        playerPanel.setBackground(Color.black);
        playerPanel.setForeground(Color.BLUE);
        playerPanel.setBounds(298, 50, 337, 251);
        contentPane.add(playerPanel);
        GridBagLayout gbl_playerPanel = new GridBagLayout();
        gbl_playerPanel.columnWidths = new int[]
        { 46, 46, 46, 46, 46, 46, 0 };
        gbl_playerPanel.rowHeights = new int[]
        { 14, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        gbl_playerPanel.columnWeights = new double[]
        { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        gbl_playerPanel.rowWeights = new double[]
        { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        playerPanel.setLayout(gbl_playerPanel);

        JLabel lblPlayer = new JLabel("Blue Player");
        lblPlayer.setBackground(Color.black);
        lblPlayer.setForeground(new Color(65, 105, 225));
        lblPlayer.setFont(FONT11);
        GridBagConstraints gbc_lblPlayer = new GridBagConstraints();
        gbc_lblPlayer.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblPlayer.insets = new Insets(0, 0, 0, 5);
        gbc_lblPlayer.gridx = 0;
        gbc_lblPlayer.gridy = 0;
        playerPanel.add(lblPlayer, gbc_lblPlayer);

        blueName = new JTextField();
        lblPlayer.setLabelFor(blueName);
        GridBagConstraints gbc_blueName = new GridBagConstraints();
        gbc_blueName.gridwidth = 5;
        gbc_blueName.insets = new Insets(0, 0, 5, 5);
        gbc_blueName.fill = GridBagConstraints.HORIZONTAL;
        gbc_blueName.gridx = 1;
        gbc_blueName.gridy = 0;
        playerPanel.add(blueName, gbc_blueName);
        blueName.setColumns(10);

        JLabel lblPlayer_1 = new JLabel("Red Player");
        lblPlayer_1.setFont(FONT11);
        lblPlayer_1.setForeground(Color.RED);
        GridBagConstraints gbc_lblPlayer_1 = new GridBagConstraints();
        gbc_lblPlayer_1.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblPlayer_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblPlayer_1.gridx = 0;
        gbc_lblPlayer_1.gridy = 1;
        playerPanel.add(lblPlayer_1, gbc_lblPlayer_1);

        redName = new JTextField();
        lblPlayer_1.setLabelFor(redName);
        GridBagConstraints gbc_redName = new GridBagConstraints();
        gbc_redName.gridwidth = 5;
        gbc_redName.insets = new Insets(0, 0, 5, 5);
        gbc_redName.fill = GridBagConstraints.HORIZONTAL;
        gbc_redName.gridx = 1;
        gbc_redName.gridy = 1;
        playerPanel.add(redName, gbc_redName);
        redName.setColumns(10);

        JLabel lblPlayer_2 = new JLabel("Light Purple Player");
        lblPlayer_2.setFont(FONT11);
        lblPlayer_2.setForeground(new Color(238, 130, 238));
        GridBagConstraints gbc_lblPlayer_2 = new GridBagConstraints();
        gbc_lblPlayer_2.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblPlayer_2.insets = new Insets(0, 0, 5, 5);
        gbc_lblPlayer_2.gridx = 0;
        gbc_lblPlayer_2.gridy = 2;
        playerPanel.add(lblPlayer_2, gbc_lblPlayer_2);

        ltPurpleName = new JTextField();
        lblPlayer_2.setLabelFor(ltPurpleName);
        GridBagConstraints gbc_ltPurpleName = new GridBagConstraints();
        gbc_ltPurpleName.gridwidth = 5;
        gbc_ltPurpleName.insets = new Insets(0, 0, 5, 5);
        gbc_ltPurpleName.fill = GridBagConstraints.HORIZONTAL;
        gbc_ltPurpleName.gridx = 1;
        gbc_ltPurpleName.gridy = 2;
        playerPanel.add(ltPurpleName, gbc_ltPurpleName);
        ltPurpleName.setColumns(10);

        JLabel lblPlayer_3 = new JLabel("Light Blue Player");
        lblPlayer_3.setFont(FONT11);
        lblPlayer_3.setForeground(new Color(0, 255, 255));
        GridBagConstraints gbc_lblPlayer_3 = new GridBagConstraints();
        gbc_lblPlayer_3.anchor = GridBagConstraints.EAST;
        gbc_lblPlayer_3.insets = new Insets(0, 0, 5, 5);
        gbc_lblPlayer_3.gridx = 0;
        gbc_lblPlayer_3.gridy = 3;
        playerPanel.add(lblPlayer_3, gbc_lblPlayer_3);

        lightBlueName = new JTextField();
        lblPlayer_3.setLabelFor(lightBlueName);
        GridBagConstraints gbc_lightBlueName = new GridBagConstraints();
        gbc_lightBlueName.gridwidth = 5;
        gbc_lightBlueName.insets = new Insets(0, 0, 5, 5);
        gbc_lightBlueName.fill = GridBagConstraints.HORIZONTAL;
        gbc_lightBlueName.gridx = 1;
        gbc_lightBlueName.gridy = 3;
        playerPanel.add(lightBlueName, gbc_lightBlueName);
        lightBlueName.setColumns(10);

        JLabel lblPlayer_4 = new JLabel("Light Green Player");
        lblPlayer_4.setFont(FONT11);
        lblPlayer_4.setForeground(new Color(127, 255, 0));
        GridBagConstraints gbc_lblPlayer_4 = new GridBagConstraints();
        gbc_lblPlayer_4.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblPlayer_4.insets = new Insets(0, 0, 5, 5);
        gbc_lblPlayer_4.gridx = 0;
        gbc_lblPlayer_4.gridy = 4;
        playerPanel.add(lblPlayer_4, gbc_lblPlayer_4);

        lightGreenName = new JTextField();
        lblPlayer_4.setLabelFor(lightGreenName);
        GridBagConstraints gbc_lightGreenName = new GridBagConstraints();
        gbc_lightGreenName.gridwidth = 5;
        gbc_lightGreenName.insets = new Insets(0, 0, 5, 5);
        gbc_lightGreenName.fill = GridBagConstraints.HORIZONTAL;
        gbc_lightGreenName.gridx = 1;
        gbc_lightGreenName.gridy = 4;
        playerPanel.add(lightGreenName, gbc_lightGreenName);
        lightGreenName.setColumns(10);

        JLabel lblPlayer_5 = new JLabel("Brown Player");
        lblPlayer_5.setFont(FONT11);
        lblPlayer_5.setForeground(new Color(139, 69, 19));
        GridBagConstraints gbc_lblPlayer_5 = new GridBagConstraints();
        gbc_lblPlayer_5.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblPlayer_5.insets = new Insets(0, 0, 5, 5);
        gbc_lblPlayer_5.gridx = 0;
        gbc_lblPlayer_5.gridy = 5;
        playerPanel.add(lblPlayer_5, gbc_lblPlayer_5);

        brownName = new JTextField();
        lblPlayer_5.setLabelFor(brownName);
        GridBagConstraints gbc_brownName = new GridBagConstraints();
        gbc_brownName.gridwidth = 5;
        gbc_brownName.insets = new Insets(0, 0, 5, 5);
        gbc_brownName.fill = GridBagConstraints.HORIZONTAL;
        gbc_brownName.gridx = 1;
        gbc_brownName.gridy = 5;
        playerPanel.add(brownName, gbc_brownName);
        brownName.setColumns(10);

        JLabel lblPlayer_6 = new JLabel("Orange Player");
        lblPlayer_6.setFont(FONT11);
        lblPlayer_6.setForeground(new Color(255, 99, 71));
        GridBagConstraints gbc_lblPlayer_6 = new GridBagConstraints();
        gbc_lblPlayer_6.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblPlayer_6.insets = new Insets(0, 0, 5, 5);
        gbc_lblPlayer_6.gridx = 0;
        gbc_lblPlayer_6.gridy = 6;
        playerPanel.add(lblPlayer_6, gbc_lblPlayer_6);

        orangeName = new JTextField();
        lblPlayer_6.setLabelFor(orangeName);
        GridBagConstraints gbc_orangeName = new GridBagConstraints();
        gbc_orangeName.gridwidth = 5;
        gbc_orangeName.insets = new Insets(0, 0, 5, 5);
        gbc_orangeName.fill = GridBagConstraints.HORIZONTAL;
        gbc_orangeName.gridx = 1;
        gbc_orangeName.gridy = 6;
        playerPanel.add(orangeName, gbc_orangeName);
        orangeName.setColumns(10);

        JLabel lblPlayer_7 = new JLabel("Green Player");
        lblPlayer_7.setFont(FONT11);
        lblPlayer_7.setForeground(new Color(46, 139, 87));
        GridBagConstraints gbc_lblPlayer_7 = new GridBagConstraints();
        gbc_lblPlayer_7.anchor = GridBagConstraints.EAST;
        gbc_lblPlayer_7.insets = new Insets(0, 0, 5, 5);
        gbc_lblPlayer_7.gridx = 0;
        gbc_lblPlayer_7.gridy = 7;
        playerPanel.add(lblPlayer_7, gbc_lblPlayer_7);

        greenName = new JTextField();
        lblPlayer_7.setLabelFor(greenName);
        GridBagConstraints gbc_greenName = new GridBagConstraints();
        gbc_greenName.gridwidth = 5;
        gbc_greenName.insets = new Insets(0, 0, 5, 5);
        gbc_greenName.fill = GridBagConstraints.HORIZONTAL;
        gbc_greenName.gridx = 1;
        gbc_greenName.gridy = 7;
        playerPanel.add(greenName, gbc_greenName);
        greenName.setColumns(10);

        JLabel lblPlayer_8 = new JLabel("Purple Player");
        lblPlayer_8.setFont(FONT11);
        lblPlayer_8.setForeground(new Color(138, 43, 226));
        GridBagConstraints gbc_lblPlayer_8 = new GridBagConstraints();
        gbc_lblPlayer_8.anchor = GridBagConstraints.EAST;
        gbc_lblPlayer_8.insets = new Insets(0, 0, 5, 5);
        gbc_lblPlayer_8.gridx = 0;
        gbc_lblPlayer_8.gridy = 8;
        playerPanel.add(lblPlayer_8, gbc_lblPlayer_8);

        purpleName = new JTextField();
        lblPlayer_8.setLabelFor(purpleName);
        GridBagConstraints gbc_purpleName = new GridBagConstraints();
        gbc_purpleName.gridwidth = 5;
        gbc_purpleName.insets = new Insets(0, 0, 5, 5);
        gbc_purpleName.fill = GridBagConstraints.HORIZONTAL;
        gbc_purpleName.gridx = 1;
        gbc_purpleName.gridy = 8;
        playerPanel.add(purpleName, gbc_purpleName);
        purpleName.setColumns(10);

        JLabel lblPlayer_9 = new JLabel("Yellow Player");
        lblPlayer_9.setFont(FONT11);
        lblPlayer_9.setForeground(new Color(255, 255, 0));
        GridBagConstraints gbc_lblPlayer_9 = new GridBagConstraints();
        gbc_lblPlayer_9.anchor = GridBagConstraints.EAST;
        gbc_lblPlayer_9.insets = new Insets(0, 0, 5, 5);
        gbc_lblPlayer_9.gridx = 0;
        gbc_lblPlayer_9.gridy = 9;
        playerPanel.add(lblPlayer_9, gbc_lblPlayer_9);

        yellowName = new JTextField();
        lblPlayer_9.setLabelFor(yellowName);
        GridBagConstraints gbc_yellowName = new GridBagConstraints();
        gbc_yellowName.gridwidth = 5;
        gbc_yellowName.insets = new Insets(0, 0, 0, 5);
        gbc_yellowName.fill = GridBagConstraints.HORIZONTAL;
        gbc_yellowName.gridx = 1;
        gbc_yellowName.gridy = 9;
        playerPanel.add(yellowName, gbc_yellowName);
        yellowName.setColumns(10);
        playerPanel.setFocusTraversalPolicy(new FocusTraversalOnArray(
                new Component[]
                { blueName, redName, ltPurpleName, lightBlueName,
                        lightGreenName, brownName, orangeName, greenName,
                        purpleName, yellowName }));

        // World settings fields
        NumberFormatter worldNumberFormat = new NumberFormatter(
                java.text.NumberFormat.getIntegerInstance());
        worldNumberFormat.setMinimum(new Integer(GameSettings.WORLDS_MIN));
        worldNumberFormat.setMaximum(new Integer(GameSettings.WORLDS_MAX));
        nWorldsTF = new JFormattedTextField(worldNumberFormat);
        nWorldsTF.setBounds(new Rectangle(217, 50, 53, 29));
        contentPane.add(nWorldsTF);

        nWorlds = new JSlider();
        ActionListener nWorldActionL = new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                nWorlds.setValue(Integer.parseInt(nWorldsTF.getText()));
            }
        };
        nWorldsTF.addActionListener(nWorldActionL);
        FocusListener nWorldFocusL = new java.awt.event.FocusListener()
        {
            public void focusGained(FocusEvent arg0)
            {
                try
                {
                    nWorlds.setValue(Integer.parseInt(nWorldsTF.getText()));
                }
                catch (Exception e)
                {
                    nWorldsTF.setText(String.valueOf(nWorlds.getValue()));
                }
            }

            public void focusLost(FocusEvent arg0)
            {
                try
                {
                    nWorlds.setValue(Integer.parseInt(nWorldsTF.getText()));
                }
                catch (Exception e)
                {
                    nWorldsTF.setText(String.valueOf(nWorlds.getValue()));
                }
            }
        };
        nWorldsTF.addFocusListener(nWorldFocusL);
        nWorlds.setBounds(new Rectangle(10, 78, 273, 43));
        nWorlds.setMinimum(2);
        nWorlds.setMajorTickSpacing(10);
        nWorlds.setMaximum(GameSettings.WORLDS_MAX);
        nWorlds.setMinorTickSpacing(1);
        nWorlds.setPaintTicks(true);
        nWorlds.createStandardLabels(10);
        nWorlds.setPaintLabels(true);
        nWorlds.setPaintTrack(true);
        nWorlds.setSnapToTicks(true);
        nWorlds.setValue(GameSettings.WORLDS_MAX / 2);
        nWorlds.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent e)
            {
                nWorldsTF.setText(String.valueOf(nWorlds.getValue()));
            }
        });
        nWorldsTF.setText(String.valueOf(nWorlds.getValue()));
        contentPane.add(nWorlds);

        nWorldsLabel = new JLabel("Number of Worlds:  ", JLabel.CENTER);
        nWorldsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nWorldsLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        nWorldsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        nWorldsLabel.setBounds(new Rectangle(10, 50, 137, 35));
        contentPane.add(nWorldsLabel);

        // Mean non-player ship production fields
        NumberFormatter uProdFormatter = new NumberFormatter(
                java.text.NumberFormat.getIntegerInstance());
        uProdFormatter.setMinimum(GameSettings.PRODUCTION_MIN);
        uProdFormatter.setMaximum(GameSettings.PRODUCTION_MAX);
        neutralProductionTF = new JFormattedTextField(uProdFormatter);
        neutralProductionTF.setText("7");
        // neutralProduction.setText(String.valueOf(PLAYER_PRODUCTION * 0.75));
        neutralProductionTF.setBounds(new Rectangle(217, 50, 53, 29));
        neutralProductionTF.setBounds(217, 132, 53, 29);
        contentPane.add(neutralProductionTF);

        neutralProduction = new JSlider();
        neutralProductionTF
                .addActionListener(new java.awt.event.ActionListener()
                {
                    public void actionPerformed(java.awt.event.ActionEvent e)
                    {
                        neutralProduction.setValue(Integer
                                .parseInt(neutralProductionTF.getText()));
                    }
                });
        neutralProduction.setValue(7);
        neutralProduction.setSnapToTicks(true);
        neutralProduction.setPaintTrack(true);
        neutralProduction.setPaintTicks(true);
        neutralProduction.setPaintLabels(true);
        neutralProduction.setMinorTickSpacing(1);
        neutralProduction.setMinimum(0);
        neutralProduction.setMaximum(25);
        neutralProduction.setMajorTickSpacing(10);
        neutralProduction.setBounds(new Rectangle(10, 81, 273, 43));
        neutralProduction
                .addChangeListener(new javax.swing.event.ChangeListener()
                {
                    public void stateChanged(javax.swing.event.ChangeEvent e)
                    {
                        neutralProductionTF.setText(String
                                .valueOf(neutralProduction.getValue()));
                    }
                });
        neutralProduction.setBounds(10, 160, 273, 43);
        contentPane.add(neutralProduction);

        JLabel lblMeanNonplayerProduction = new JLabel(
                "Mean Non-Player Production:", SwingConstants.LEFT);
        lblMeanNonplayerProduction
                .setHorizontalTextPosition(SwingConstants.LEFT);
        lblMeanNonplayerProduction.setBounds(new Rectangle(10, 50, 137, 35));
        lblMeanNonplayerProduction.setAlignmentX(0.5f);
        lblMeanNonplayerProduction.setBounds(10, 132, 183, 35);
        contentPane.add(lblMeanNonplayerProduction);

        // Mean non-player ship fields
        NumberFormatter uShipsInitFormatter = new NumberFormatter(
                java.text.NumberFormat.getIntegerInstance());
        uShipsInitFormatter.setMinimum(GameSettings.SHIPS_INIT_MIN);
        uShipsInitFormatter.setMaximum(GameSettings.SHIPS_INIT_MAX);
        neutralShipsInitTF = new JFormattedTextField(uShipsInitFormatter);
        neutralShipsInitTF.setText("40");
        neutralShipsInitTF.setBounds(217, 214, 53, 29);
        contentPane.add(neutralShipsInitTF);

        neutralShipsInit = new JSlider();
        neutralShipsInitTF
                .addActionListener(new java.awt.event.ActionListener()
                {
                    public void actionPerformed(java.awt.event.ActionEvent e)
                    {
                        neutralShipsInit.setValue(Integer
                                .parseInt(neutralShipsInitTF.getText()));
                    }
                });
        neutralShipsInit.setValue(40);
        neutralShipsInit.setSnapToTicks(true);
        neutralShipsInit.setPaintTrack(true);
        neutralShipsInit.setPaintTicks(true);
        neutralShipsInit.setPaintLabels(true);
        neutralShipsInit.setMinorTickSpacing(1);
        neutralShipsInit.setMajorTickSpacing(10);
        neutralShipsInit.setBounds(new Rectangle(10, 131, 273, 43));
        neutralShipsInit
                .addChangeListener(new javax.swing.event.ChangeListener()
                {
                    public void stateChanged(javax.swing.event.ChangeEvent e)
                    {
                        neutralShipsInitTF.setText(String
                                .valueOf(neutralShipsInit.getValue()));
                    }
                });
        neutralShipsInit.setBounds(10, 242, 273, 43);
        contentPane.add(neutralShipsInit);

        JLabel lblMeanNonplayerShipsInit = new JLabel(
                "Mean Non-Player Start Ships:", SwingConstants.LEFT);
        lblMeanNonplayerShipsInit
                .setHorizontalTextPosition(SwingConstants.LEFT);
        lblMeanNonplayerShipsInit.setAlignmentX(0.5f);
        lblMeanNonplayerShipsInit.setBounds(10, 214, 183, 35);
        contentPane.add(lblMeanNonplayerShipsInit);

        final String ProdFalse = "Neutral worlds DO NOT produce ships.";
        final String ProdTrue = "Neutral worlds produce ships.";
        final JToggleButton doNeutralsProduce = new JToggleButton(ProdFalse);
        doNeutralsProduce.setBounds(10, 309, 260, 23);
        doNeutralsProduce.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                if (doNeutralsProduce.isSelected() == true)
                {
                    doNeutralsProduce.setText(ProdTrue);
                }
                else
                {
                    doNeutralsProduce.setText(ProdFalse);
                }
            }
        });
        contentPane.add(doNeutralsProduce);

        // Mean non-player ship fields
        NumberFormatter lastYearFormatter = new NumberFormatter(
                java.text.NumberFormat.getIntegerInstance());
        lastYearFormatter.setMinimum(1);
        lastYearTF = new JFormattedTextField(lastYearFormatter);
        lastYearTF.setText("20");
        lastYearTF.setBounds(217, 343, 53, 29);
        contentPane.add(lastYearTF);

        JButton btnOKButton = new JButton("Start Game");
        btnOKButton.setBounds(507, 349, 128, 23);
        btnOKButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {

                GameSettings settings = game.getSettings();
                try
                {
                    settings.setLastYear(Integer.parseInt(lastYearTF.getText()));
                }
                catch (Exception e)
                {
                    lastYearTF.requestFocusInWindow();
                    return;
                }

                try
                {
                    settings.setMeanProduction(Integer
                            .parseInt(neutralProductionTF.getText()));
                }
                catch (Exception e)
                {
                    neutralProductionTF.requestFocusInWindow();
                    return;
                }

                try
                {
                    settings.setMeanShips(Integer.parseInt(neutralShipsInitTF
                            .getText()));
                }
                catch (Exception e)
                {
                    neutralShipsInitTF.requestFocusInWindow();
                    return;
                }
                try
                {
                    settings.setNumWorlds(Integer.parseInt(nWorldsTF.getText()));
                }
                catch (Exception e)
                {
                    nWorldsTF.requestFocusInWindow();
                    return;
                }

                settings.setNeutralProdOn(doNeutralsProduce.isSelected());
                settings.setPlayers(this_this.getPlayers());
                int nPlayers = settings.getPlayers().length;
                if (nPlayers <= 0)
                {
                    blueName.requestFocusInWindow();
                    return;
                }
                if (nPlayers > settings.getNumWorlds())
                {
                    nWorldsTF.setText(String.valueOf(nPlayers));
                    nWorldsTF.requestFocusInWindow();
                    return;
                }

                settings.setPlayerProduction(GameSettings.PLAYER_PRODUCTION);
                setVisible(false);
                game.startGame();

                // dispose();
            }
        });
        contentPane.add(btnOKButton);

        JLabel lblNumberOfGame = new JLabel("Number of game years:");
        lblNumberOfGame.setBounds(10, 343, 197, 29);
        contentPane.add(lblNumberOfGame);

        JButton instructions = new JButton("Instructions");
        instructions.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                instructW = new TextWindow(
                        "Empire Instructions",
                        "Empire is a multiplayer game of conquest.  It is played on a 20x20 grid containing worlds.  "
                                + "Players attempt to control more worlds at the end of the game than their opponents.\r\n"
                                + "\r\n"
                                + "Play\r\n"
                                + "Play is turn based; each set of player turns is called a year.  In each year, each player "
                                + "has an opportunity to launch fleets of ships.  It is best to arrange for this to be done "
                                + "secretly.  After all players have given orders for their fleet launchs, the year proceeds through a sequence "
                                + "of fleet arrivals, launching fleets, and worlds producing ships.  In a 20-year game, there are 20 "
                                + "opportunities to launch fleets; so, the game will end after fleet arrivals in year 21.\r\n"
                                + "\r\n"
                                + "Orders\r\n"
                                + "Orders for fleets consist of entering the letter of the world from which the fleet will be launched, "
                                + "the letter of the world to which they are being sent, and how many to send.  An order is final once  "
                                + "the number of ships to send is 'Enter'ed.  Ending your turn without finalizing an order will result "
                                + "in that order not being carried out.\n\r"
                                + "\r\n"
                                + "Fleet travel is modeled as speeding up until they reach the halfway point, then slowing down until "
                                + "the reach the destination.  Acceleration is 2 grids/year/year; you can see the travel time near the "
                                + "fleet command fields.  You can also see travel time between worlds that you do not control by entering "
                                + "them as if you were launching a fleet, but you will not be allowed to enter a number of ships.\n\r"
                                + "\r\n"
                                + "Fleets arrive at the beginning of a year.  Do to the vagaries of space travel, the arrival order of fleets within "
                                + "a year is effectively random.  If the owner of the fleet is the same as the owner of the planet when it arrives "
                                + "the fleet becomes reinforments; else, it becomes an attacking force.\n\r"
                                + "\r\n"
                                + "Winning is simply determined by who controls the most planets at the end of the game.  If you "
                                + "have more ships than someone with more planets at the end, try playing more aggressively.");
                instructW.run();
            }
        });
        instructions.setBounds(369, 349, 128, 23);
        contentPane.add(instructions);
    }

    private Player[] getPlayers()
    {
        ArrayList<Player> playerTemp = new ArrayList<Player>();

        // create players from non-empty name fields
        Component items[] = playerPanel.getComponents();
        for (int i = 0; i < items.length; i++)
        {
            if (items[i] instanceof JLabel)
            {
                JLabel item = (JLabel) items[i];
                JTextField nameFld = (JTextField) (item.getLabelFor());
                String name = nameFld.getText().trim();
                if (name.length() > 0)
                {
                    playerTemp.add(new Player(name, item.getForeground()));
                }
            }
        }

        Player[] players = new Player[playerTemp.size()];
        players = playerTemp.toArray(players);
        return players;
    }
}
