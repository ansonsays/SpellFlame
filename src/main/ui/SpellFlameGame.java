package ui;

import model.Spell;
import model.Hero;
import model.Player;
import model.Enemy;
import persistence.SaveGameReader;
import persistence.SaveGameWriter;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*
 * Spellflame game
 */
public class SpellFlameGame extends JFrame {
    private Player player;
    private Enemy enemy;
    private Spell mage1;
    private Spell mage2;
    private Spell mage3;
    private Spell mage4;
    private Spell mage5;
    private Spell enemy0;
    private Spell enemy1;
    private Spell enemy2;
    private Spell enemy3;
    private Spell enemy4;
    private Spell enemy5;
    private ArrayList<Spell> mageSpells = new ArrayList<Spell>();
    private boolean playerAlive; // boolean to track player status
    private boolean enemyAlive; // boolean to track enemy status
    private Scanner input = new Scanner(System.in);
    private String playerName;
    private String enemyName;
    private static final String JSON_STORE = "./data/savegame.json";
    private SaveGameWriter saveGameWriter;
    private SaveGameReader saveGameReader;
    private JFrame frame;
    private JPanel titlePanel;
    private JPanel startButtonPanel;
    private JPanel namePanel;
    private JPanel spellSelectPanel;
    private JPanel preFightPanel;
    private JPanel learnSpellPanel;
    private JPanel levelUpPanel;
    private JPanel fightPanel;
    private JPanel castSpellPanel;
    private JPanel battleResultPanel;
    private JLabel label;
    private JLabel pictureLabel;
    private JTextField nameField;
    private ArrayList<JButton> preFightButtonList = new ArrayList<JButton>();
    private ArrayList<JButton> fightButtonList = new ArrayList<JButton>();
    private JButton spellListButton;
    private JButton fightButton;
    private JButton restButton;
    private JButton learnSpellButton;
    private JButton upgradeSpellButton;
    private JButton saveButton;
    public static final int WIDTH = 620;
    public static final int HEIGHT = 450;

    // EFFECTS: runs the Spellflame game
    public SpellFlameGame() {
        frame = new JFrame("Spellflame");
        initializeGameWindow();
        startScreenMenu();
        runGame();
    }

    // MODIFIES: this
    // EFFECTS: runs the process that make up the game
    private void runGame() {
        playerAlive = true;

        saveGameWriter = new SaveGameWriter(JSON_STORE);
        saveGameReader = new SaveGameReader(JSON_STORE);

        spellHeroInit();

        System.out.println("\nWelcome to Spellflame!");
        System.out.println("\t1) Start!");
        System.out.println("\t2) Load game");
    }

    // Initializes the game window
    // MODIFIES: this
    // EFFECTS: draws the JFrame window where the game will operate, and populates it with the buttons necessary to play
    private void initializeGameWindow() {
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLocationRelativeTo(null);
        //frame.setVisible(true);
        preFightMenuButtonCreator();
        fightMenuButtonCreator();
    }

    // Creates and populates the start screen
    // MODIFIES: this
    // EFFECTS: creates and adds the panels and buttons for the start screen
    // Note: title image created using https://textcraft.net/
    private void startScreenMenu() {
        startButtonPanel = new JPanel();
        startButtonPanel.setLayout(new GridLayout(2,1));
        startButtonPanel.setSize(new Dimension(0, 0));
        titlePanel = new JPanel();
        frame.add(titlePanel, BorderLayout.CENTER);
        frame.add(startButtonPanel, BorderLayout.SOUTH);
        ImageIcon icon = new ImageIcon("./data/title.png");
        pictureLabel = new JLabel(icon);
        pictureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(pictureLabel);
        JButton startButton = new JButton("Start Game");
        startButton.setActionCommand("Start Game");
        startButton.addActionListener(new StartScreenClickHandler());
        JButton loadButton = new JButton("Load Game");
        loadButton.setActionCommand("Load Game");
        loadButton.addActionListener(new StartScreenClickHandler());
        startButtonPanel.add(startButton);
        startButtonPanel.add(loadButton);
        frame.setVisible(true);
    }

    // Processes player input from the start screen
    // MODIFIES: this
    // EFFECTS: processes player command
    private void processStartScreenCommand(int command) {
        if (command == 1) {
            nameMenu();
            switchPanel(namePanel, startButtonPanel);
            System.out.println("\nInput your Hero's name: ");
        } else if (command == 2) {
            loadGame();
        }
    }

    // Creates and populates the name menu where the player inputs their name
    // MODIFIES: this
    // EFFECTS: creates and adds the panels and buttons for the name menu
    private void nameMenu() {
        namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout());
        namePanel.setSize(new Dimension(0, 0));
        JButton setNameButton = new JButton("Continue");
        setNameButton.setActionCommand("Continue");
        setNameButton.addActionListener(new StartScreenClickHandler());
        label = new JLabel("Input your Hero's name: ");
        nameField = new JTextField(5);
        namePanel.add(label);
        namePanel.add(nameField);
        namePanel.add(setNameButton);
        pack();
        setLocationRelativeTo(null);
    }

    // Processes player input from the name menu
    // MODIFIES: this
    // EFFECTS: processes player input and creates the player's Hero object
    private void processNameMenu(String name) {
        playerName = name;
        player = new Player(playerName, 1, 25, 25, 0, 0, false);
        //displaySpellSelectMenu();
        spellSelectMenu();
        switchPanel(spellSelectPanel, namePanel);
    }

    // MODIFIES: this
    // EFFECTS: initializes Spells and player Hero
    private void spellHeroInit() {
        mage1 = new Spell("Fireball", 1, "Fire");
        mage2 = new Spell("Earth Spear", 1, "Earth");
        mage3 = new Spell("Lightning Bolt", 1, "Lightning");
        mage4 = new Spell("Water Cyclone", 1, "Water");
        mage5 = new Spell("Wind Blast", 1, "Wind");
        mageSpells.add(mage1);
        mageSpells.add(mage2);
        mageSpells.add(mage3);
        mageSpells.add(mage4);
        mageSpells.add(mage5);
        enemy0 = new Spell("Slash", -1, "None");
    }

    // Menu at the start of the game where the player chooses their first Spell
    // MODIFIES: this
    // EFFECTS: displays Spell selection menu and takes player input
    private void displaySpellSelectMenu() {
        spellSelectMenu();
        switchPanel(spellSelectPanel, namePanel);
        int count = 0;
        int increment = 1;
        System.out.println("\nChoose your starting spell!");
        while (mageSpells.size() > count) {
            System.out.println("\t" + increment + ") " + mageSpells.get(count).toString());
            count++;
            increment++;
        }
        System.out.println("\t0) \tQuit");

        int command = input.nextInt();

        if (command == 0) {
            System.out.println("\nExiting game...");
            System.exit(0);
        } else {
            processSpellSelectCommand(command);
        }
    }

    // Creates and populates the spell select menu where the player chooses their first Spell
    // MODIFIES: this
    // EFFECTS: creates and adds the panels and buttons for the Spell select menu
    private void spellSelectMenu() {
        spellSelectPanel = new JPanel();
        spellSelectPanel.setLayout(new BoxLayout(spellSelectPanel, BoxLayout.Y_AXIS));
        spellSelectPanel.setSize(new Dimension(0, 0));
        frame.add(spellSelectPanel, BorderLayout.SOUTH);
        label = new JLabel("Choose your starting spell!");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        spellSelectPanel.add(label);

        for (int count = 0; count < mageSpells.size(); count++) {
            JButton spellButton = new JButton(mageSpells.get(count).toString());
            spellSelectPanel.add(spellButton);
            spellButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            spellButton.setActionCommand(Integer.toString(count + 1));
            spellButton.addActionListener(new SelectSpellClickHandler());
        }
        pack();
        setLocationRelativeTo(null);
    }

    // Processes player input from the spell select menu
    // REQUIRES: command >= 0 && command <= 5
    // MODIFIES: this
    // EFFECTS: processes player command
    private void processSpellSelectCommand(int command) {
        if (command == 1) {
            player.learnSpell(mage1);
        } else if (command == 2) {
            player.learnSpell(mage2);
        } else if (command == 3) {
            player.learnSpell(mage3);
        } else if (command == 4) {
            player.learnSpell(mage4);
        } else if (command == 5) {
            player.learnSpell(mage5);
        } else {
            System.out.println("Invalid selection.");
        }
        System.out.println("You have learned " + mageSpells.get(command - 1).getName() + "!");
        JOptionPane.showMessageDialog(frame, "You have learned " + mageSpells.get(command - 1).getName() + "!");
        mageSpells.remove(command - 1);
        player.setSpellSwitch(false);
        //displayPreFightMenu();
        preFightMenu();
        switchPanel(preFightPanel, spellSelectPanel);
    }

    // Menu between each fight where the player can prepare for the next fight
    // MODIFIES: this
    // EFFECTS: displays the pre-fight menu and takes player input
    private void displayPreFightMenu() {
        while (playerAlive) {
            System.out.println("\n\n\n_______________________\nYou are " + player.toString());
            System.out.println("\n\nYou have " + player.getCurrentHealth() + "/" + player.getMaxHealth() + " health.");
            System.out.println("You have slain " + player.getEnemiesKilled() + " enemies.");
            System.out.println("\nWhat would you like to do?");
            System.out.println("\t1) View spell list");
            System.out.println("\t2) Fight!");
            if (player.getStage() != 0) {
                System.out.println("\t3) Rest");
                System.out.println("\t4) Learn new spell");
                System.out.println("\t5) Upgrade spell");
            }
            System.out.println("\t9) Save game");
            System.out.println("\t0) Quit");

            int command = input.nextInt();

            if (command == 0) {
                System.out.println("\nExiting game...");
                System.exit(0);
            } else {
                processPreFightCommand(command);
            }
        }
    }

    // Creates and populates the pre-fight menu where the player can prepare for the next fight
    // MODIFIES: this
    // EFFECTS: creates and adds the panels and buttons for the pre-fight menu
    private void preFightMenu() {
        preFightPanel = new JPanel();
        preFightPanel.setLayout(new BoxLayout(preFightPanel, BoxLayout.Y_AXIS));
        preFightPanel.setSize(new Dimension(0, 0));
        frame.add(preFightPanel, BorderLayout.NORTH);
        preFightMenuAnimation();
        label = new JLabel("<html><div style='text-align: center;'>You are " + player.toString() + "<br/>You have "
                + player.getCurrentHealth() + "/" + player.getMaxHealth() + " health. <br/>You have slain "
                + player.getEnemiesKilled() + " enemies.<br/>What would you like to do?</div></html", JLabel.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        preFightPanel.add(label);
        preFightPanel.add(pictureLabel);
        preFightMenuButtonAdder();
        if (player.getStage() == 0) {
            restButton.setEnabled(false);
            learnSpellButton.setEnabled(false);
            upgradeSpellButton.setEnabled(false);
        } else {
            restButton.setEnabled(true);
            learnSpellButton.setEnabled(true);
            upgradeSpellButton.setEnabled(true);
        }
        pack();
        setLocationRelativeTo(null);
    }

    // Helper method to initialize the campfire gif for the pre-fight menu
    // MODIFIES: this
    // EFFECTS: creates a JLabel containing the gif
    // Note: gif taken from https://www.pinterest.ca/pin/627618898061122577/
    private void preFightMenuAnimation() {
        ImageIcon icon = new ImageIcon("./data/campfire.gif");
        pictureLabel = new JLabel(icon);
        pictureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    // Helper method to create the buttons that will populate the pre-fight menu
    // MODIFIES: this
    // EFFECTS: creates and adds JButtons to a list
    private void preFightMenuButtonCreator() {
        preFightButtonList.add(spellListButton = new JButton("View Spell list"));
        spellListButton.setActionCommand(spellListButton.getText());
        spellListButton.addActionListener(new PreFightMenuClickHandler());
        preFightButtonList.add(fightButton = new JButton("Fight!"));
        fightButton.setActionCommand(fightButton.getText());
        fightButton.addActionListener(new PreFightMenuClickHandler());
        preFightButtonList.add(restButton = new JButton("Rest"));
        restButton.setActionCommand(restButton.getText());
        restButton.addActionListener(new PreFightMenuClickHandler());
        preFightButtonList.add(learnSpellButton = new JButton("Learn new Spell"));
        learnSpellButton.setActionCommand(learnSpellButton.getText());
        learnSpellButton.addActionListener(new PreFightMenuClickHandler());
        preFightButtonList.add(upgradeSpellButton = new JButton("Upgrade Spell"));
        upgradeSpellButton.setActionCommand(upgradeSpellButton.getText());
        upgradeSpellButton.addActionListener(new PreFightMenuClickHandler());
        preFightButtonList.add(saveButton = new JButton("Save Game"));
        saveButton.setActionCommand(saveButton.getText());
        saveButton.addActionListener(new PreFightMenuClickHandler());
    }

    // Helper method to add buttons to the pre-fight menu
    // MODIFIES: this
    // EFFECTS: adds all buttons in preFightButtonList to preFightPanel
    private void preFightMenuButtonAdder() {
        for (JButton button : preFightButtonList) {
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            preFightPanel.add(button);
        }
    }

    // Processes user input from the pre-fight menu
    // REQUIRES: command >= 0 && command <= 5
    // MODIFIES: this
    // EFFECTS: processes user command
    private void processPreFightCommand(int command) {
        if (command == 1) {
            //System.out.println(player.showSpells());
            JOptionPane.showMessageDialog(frame, player.showSpells());
        //} else if (command == 2) {
            //startFight();
        } else if (command == 3 || command == 4 || command == 5) {
            if (player.getSpellSwitch()) {
                JOptionPane.showMessageDialog(frame, "You can only recover, learn or upgrade once between each fight!");
            } else {
                if (command == 3) {
                    JOptionPane.showMessageDialog(frame, "You rest and recover your health.");
                    player.setCurrentHealth(player.getMaxHealth());
                    player.setSpellSwitch(true);
                    preFightPanel.removeAll();
                    preFightMenu();
                    preFightPanel.revalidate();
                    preFightPanel.repaint();
                } else if (command == 4 && player.getNumberOfSpells() < 3) {
                    //displayLearnSpellMenu();
                    learnSpellMenu();
                } else if (command == 4 && player.getNumberOfSpells() >= 3) {
                    JOptionPane.showMessageDialog(frame, "You can't learn any more Spells!");
                } else if (command == 5) {
                    //displayLevelUpMenu();
                    levelUpMenu();
                }
            }
        //} else if (command == 9) {
            //saveGame();
        }
    }

    // Menu where the player can learn a new Spell
    // MODIFIES: this
    // EFFECTS: displays Spell learning menu and takes player input
    private void displayLearnSpellMenu() {
        int count = 0;
        int increment = 1;
        System.out.println("Which Spell would you like to learn?");
        while (mageSpells.size() > count) {
            System.out.println("\t" + increment + ") " + mageSpells.get(count).toString());
            count++;
            increment++;
        }
        System.out.println("\t0) \tCancel");

        int command = input.nextInt();

        if (command == 0) {
            return;
        } else {
            processLearnSpellMenuCommand(command);
        }
    }

    // Creates and populates the learn Spell menu where the player can learn a new Spell
    // MODIFIES: this
    // EFFECTS: creates and adds the panels and buttons for the learn Spell menu
    private void learnSpellMenu() {
        learnSpellPanel = new JPanel();
        learnSpellPanel.setLayout(new BoxLayout(learnSpellPanel, BoxLayout.Y_AXIS));
        learnSpellPanel.setSize(new Dimension(0, 0));
        frame.add(learnSpellPanel, BorderLayout.SOUTH);
        label = new JLabel("What Spell would you like to learn?");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        learnSpellPanel.add(label);

        for (int count = 0; count < mageSpells.size(); count++) {
            JButton spellButton = new JButton(mageSpells.get(count).toString());
            learnSpellPanel.add(spellButton);
            spellButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            spellButton.setActionCommand(Integer.toString(count + 1));
            spellButton.addActionListener(new LearnSpellClickHandler());
        }
        JButton cancelButton = new JButton("Cancel");
        learnSpellPanel.add(cancelButton);
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.setActionCommand("0");
        cancelButton.addActionListener(new LearnSpellClickHandler());
        pack();
        setLocationRelativeTo(null);
        switchPanel(learnSpellPanel, preFightPanel);
    }

    // Processes user input from the learn Spell menu
    // REQUIRES: command >= 0 || command <= 5
    // MODIFIES: this
    // EFFECTS: processes user command
    private void processLearnSpellMenuCommand(int command) {
        //System.out.println("You have learned " + mageSpells.get(command - 1).getName() + "!");
        if (command == 0) {
            preFightMenu();
            switchPanel(preFightPanel, learnSpellPanel);
            return;
        }
        JOptionPane.showMessageDialog(frame, "You have learned " + mageSpells.get(command - 1).getName() + "!");
        player.learnSpell(mageSpells.get(command - 1));
        mageSpells.remove(command - 1);
        player.setSpellSwitch(true);
        preFightMenu();
        switchPanel(preFightPanel, learnSpellPanel);
    }

    // Menu where the player can level up a spell
    // MODIFIES: this
    // EFFECTS: displays level up menu and takes player input
    private void displayLevelUpMenu() {
        System.out.println("\nWhich Spell would you like to improve?");
        System.out.println(player.showSpells());
        System.out.println("\t0) \tCancel");

        int command = input.nextInt();

        if (command == 0) {
            return;
        } else {
            processLevelUpMenuCommand(command);
        }
    }

    // Creates and populates the level up menu where the player can level up a Spell
    // MODIFIES: this
    // EFFECTS: creates and adds the panels and buttons for the level up menu
    private void levelUpMenu() {
        levelUpPanel = new JPanel();
        levelUpPanel.setLayout(new BoxLayout(levelUpPanel, BoxLayout.Y_AXIS));
        levelUpPanel.setSize(0, 0);
        frame.add(levelUpPanel, BorderLayout.SOUTH);
        label = new JLabel("Which Spell would you like to improve?");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelUpPanel.add(label);
        for (int count = 0; count < player.getSpellList().size(); count++) {
            JButton spellButton = new JButton(player.getSpellList().get(count).toString());
            levelUpPanel.add(spellButton);
            spellButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            spellButton.setActionCommand(Integer.toString(count + 1));
            spellButton.addActionListener(new LevelUpClickHandler());
        }
        JButton cancelButton = new JButton("Cancel");
        levelUpPanel.add(cancelButton);
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.setActionCommand("0");
        cancelButton.addActionListener(new LevelUpClickHandler());
        pack();
        setLocationRelativeTo(null);
        switchPanel(levelUpPanel, preFightPanel);
    }

    // Processes player input from the level up menu
    // REQUIRES: command >= 0 || command <= 3
    // MODIFIES: this
    // EFFECTS: processes player command
    private void processLevelUpMenuCommand(int command) {
        if (command == 1) {
            player.levelUp();
            player.selectSpell(command).levelUp();
        } else if (command == 2) {
            player.levelUp();
            player.selectSpell(command).levelUp();
        } else if (command == 3) {
            player.levelUp();
            player.selectSpell(command).levelUp();
        } else if (command == 0) {
            preFightMenu();
            switchPanel(preFightPanel, levelUpPanel);
            return;
        }
        String text = "Success! Your spell is now: " + player.selectSpell(command).toString()
                + ". \nYou have leveled up! You now have a maximum of " + player.getMaxHealth() + " hit points!";
        System.out.println(text);
        JOptionPane.showMessageDialog(frame, text);
        player.setSpellSwitch(true);
        preFightMenu();
        switchPanel(preFightPanel, levelUpPanel);
    }

    // Starts a fight between the player and a randomly generated enemy Hero
    // MODIFIES: this
    // EFFECTS: calls the methods required to start a fight
    private void startFight() {
        player.setStage(player.getStage() + 1);
        enemyAlive = true;
        player.setSpellSwitch(false);

        generateEnemy(player.getStage());
        System.out.println("A new enemy has appeared! You find yourself face to face with " + enemy.toString() + "!");
        JOptionPane.showMessageDialog(frame, "A new enemy has appeared! You find yourself face to face with "
                + enemy.toString() + "!");
        fightMenu();
        switchPanel(fightPanel, preFightPanel);
    }

    // Generates an enemy Hero and gives it Spells. Enemy strength scales with stage number.
    // REQUIRES: stage >= 1
    // MODIFIES: this
    // EFFECTS: Creates an enemy Hero and adds a random Spell to it
    private void generateEnemy(int stage) {
        enemy1 = new Spell("Fiery Rage", stage, "Fire");
        enemy2 = new Spell("Earthshaking Wrath", stage, "Earth");
        enemy3 = new Spell("Shocking Screech", stage, "Lightning");
        enemy4 = new Spell("Liquid Fury", stage, "Water");
        enemy5 = new Spell("Howling Wind", stage, "Wind");
        enemyName = nameRandomizer();
        enemy = new Enemy(enemyName, stage, 25 + stage - 1);
        enemy.learnSpell(enemy0);
        int rand = (int)(Math.random() * 5 + 1);

        if (rand == 1) {
            enemy.learnSpell(enemy1);
        } else if (rand == 2) {
            enemy.learnSpell(enemy2);
        } else if (rand == 3) {
            enemy.learnSpell(enemy3);
        } else if (rand == 4) {
            enemy.learnSpell(enemy4);
        } else {
            enemy.learnSpell(enemy5);
        }
    }

    // Randomly selects a name for the enemy Hero
    // MODIFIES: this
    // EFFECTS: returns a randomly selected name for the enemy
    private String nameRandomizer() {
        String str;
        int rand = (int)(Math.random() * 5 + 1);
        if (rand == 1) {
            str = "Dread Knight";
        } else if (rand == 2) {
            str = "Dark Mage";
        } else if (rand == 3) {
            str = "Foul Ghoul";
        } else if (rand == 4) {
            str = "Frenzied Fiend";
        } else {
            str = "Fallen Lord";
        }
        return str;
    }

    // Menu where the player fights an enemy
    // MODIFIES: this
    // EFFECTS: displays combat menu and takes player input
    private void displayFightMenu() {
        System.out.println("\n\nYou have " + player.getCurrentHealth() + "/" + player.getMaxHealth() + " health.");
        System.out.print(enemy.getName() + " has " + enemy.getCurrentHealth() + "/" + enemy.getMaxHealth());
        System.out.println(" health.");

        System.out.println("\nWhat would you like to do?");
        System.out.println("\t1) Cast a spell");
        System.out.println("\t2) Recover (heals 3 to 11 hit points)");

        int command = input.nextInt();
        processCombatMenuCommand(command);

    }

    // Creates and populates the fight menu where the player decides how to fight an enemy
    // MODIFIES: this
    // EFFECTS: creates and adds the panels and buttons for the fight menu
    private void fightMenu() {
        fightPanel = new JPanel();
        fightPanel.setLayout(new BoxLayout(fightPanel, BoxLayout.Y_AXIS));
        fightPanel.setSize(0, 0);
        frame.add(fightPanel, BorderLayout.SOUTH);
        label = new JLabel("You have " + player.getCurrentHealth() + "/" + player.getMaxHealth() + " health.");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        fightPanel.add(label);
        label = new JLabel(enemy.getName() + " has " + enemy.getCurrentHealth() + "/" + enemy.getMaxHealth()
                + " health");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        fightPanel.add(label);
        enemyImageDisplay(enemy.getName());
        fightPanel.add(pictureLabel);
        label = new JLabel("What would you like to do?");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        fightPanel.add(label);
        fightMenuButtonAdder();
        pack();
        setLocationRelativeTo(null);
    }

    // Helper method to create the buttons that will populate the fight menu
    // MODIFIES: this
    // EFFECTS: creates and adds JButtons to a list
    private void fightMenuButtonCreator() {
        JButton castSpellButton = new JButton("Cast a Spell");
        castSpellButton.setActionCommand("Cast a Spell");
        castSpellButton.addActionListener(new FightMenuClickHandler());
        fightButtonList.add(castSpellButton);
        JButton recoveryButton = new JButton("Recover (heals 3 to 11 hit points)");
        recoveryButton.setActionCommand("Recover");
        recoveryButton.addActionListener(new FightMenuClickHandler());
        fightButtonList.add(recoveryButton);
    }

    // Helper method to add buttons to the fight menu
    // MODIFIES: this
    // EFFECTS: adds all buttons in fightButtonList to fightPanel
    private void fightMenuButtonAdder() {
        for (JButton button : fightButtonList) {
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            fightPanel.add(button);
        }
    }

    // Helper method to display the enemy image
    // MODIFIES: this
    // EFFECTS: adds an image of the enemy to fightPanel
    // Note: images are from RPG Maker VX Ace template files
    private void enemyImageDisplay(String enemy) {
        ImageIcon icon = null;
        if (enemy.equals("Dread Knight")) {
            icon = new ImageIcon("./data/dreadKnight.png");
        } else if (enemy.equals("Dark Mage")) {
            icon = new ImageIcon("./data/darkMage.png");
        } else if (enemy.equals("Foul Ghoul")) {
            icon = new ImageIcon("./data/foulGhoul.png");
        } else if (enemy.equals("Frenzied Fiend")) {
            icon = new ImageIcon("./data/frenziedFiend.png");
        } else if (enemy.equals("Fallen Lord")) {
            icon = new ImageIcon("./data/fallenLord.png");
        }
        pictureLabel = new JLabel(icon);
        pictureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    // Processes player input from the fight menu
    // REQUIRES: command == 1 || command == 2
    // MODIFIES: this
    // EFFECTS: processes player command
    private void processCombatMenuCommand(int command) {
        int healAmount = (int)(Math.random() * 9 + 3);
        if (command == 1) {
            //displayCastSpellMenu();
            castSpellMenu();
            switchPanel(castSpellPanel, fightPanel);
        } else if (command == 2) {
            if (player.getCurrentHealth() == player.getMaxHealth()) {
                System.out.println("You already have full health!");
                JOptionPane.showMessageDialog(frame, "You already have full health!");
            } else {
                player.heal(healAmount);
                System.out.println("You take a moment to recover and have healed " + healAmount + " hit points.");
                JOptionPane.showMessageDialog(frame, "You take a moment to recover and have healed " + healAmount
                        + " hit points.");
                spellDuel(null);
            }
        } 
    }

    // Menu where the player selects a Spell to cast
    // MODIFIES: this
    // EFFECTS: displays cast Spell menu and takes player input
    private void displayCastSpellMenu() {
        System.out.println("\nWhich spell would you like to cast?");
        System.out.println(player.showSpells());
        System.out.println("\t0) \tCancel");

        int command = input.nextInt();
        if (command == 0) {
            return;
        } else {
            //processCastSpellMenuCommand(command);
            System.out.println("You cast " + player.selectSpell(command).getName());
            spellDuel(player.selectSpell(command));
        }
    }

    // Creates and populates the Spell cast menu where the player selects a Spell to cast
    // MODIFIES: this
    // EFFECTS: creates and adds the panels and buttons for the Spell cast menu
    private void castSpellMenu() {
        castSpellPanel = new JPanel();
        castSpellPanel.setLayout(new BoxLayout(castSpellPanel, BoxLayout.Y_AXIS));
        castSpellPanel.setSize(0, 0);
        frame.add(castSpellPanel, BorderLayout.SOUTH);
        label = new JLabel("Which Spell would you like to cast?");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        fightPanel.add(label);
        for (int count = 0; count < player.getSpellList().size(); count++) {
            JButton spellButton = new JButton(player.getSpellList().get(count).toString());
            castSpellPanel.add(spellButton);
            spellButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            spellButton.setActionCommand(Integer.toString(count + 1));
            spellButton.addActionListener(new CastSpellClickHandler());
        }
        JButton cancelButton = new JButton("Cancel");
        castSpellPanel.add(cancelButton);
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.setActionCommand("0");
        cancelButton.addActionListener(new CastSpellClickHandler());
        pack();
        setLocationRelativeTo(null);
    }

    // Processes player input from the Spell cast menu
    // REQUIRES: command >= 0 && command <= 3
    // MODIFIES: this
    // EFFECTS: processes player command
    private void processCastSpellCommand(int command) {
        if (command == 0) {
            fightMenu();
            switchPanel(fightPanel, castSpellPanel);
        } else {
            System.out.println("You cast " + player.selectSpell(command).getName());
            JOptionPane.showMessageDialog(frame, "You cast " + player.selectSpell(command).getName() + "!");
            spellDuel(player.selectSpell(command));
        }
    }

    // Takes the player's Spell, creates an enemy Spell, then sends them both to the damage calculator
    // MODIFIES: this
    private void spellDuel(Spell playerSpell) {
        Spell enemySpell;
        enemySpell = enemyMove();
        damageCalculator(playerSpell, enemySpell);
    }

    // Randomly decides what the enemy will do
    // MODIFIES: this
    // EFFECTS: returns a Spell if the enemy casts a Spell; returns if the enemy does not
    private Spell enemyMove() {
        int rand = (int)(Math.random() * 4 + 1);
        if (rand == 1) {
            System.out.println(enemy.getName() + " casts " + enemy.selectSpell(1).getName() + "!");
            JOptionPane.showMessageDialog(frame, enemy.getName() + " casts " + enemy.selectSpell(1).getName() + "!");
            return enemy.selectSpell(1);
        } else if (rand == 2 || rand == 3) {
            System.out.println(enemy.getName() + " casts " + enemy.selectSpell(2).getName() + "!");
            JOptionPane.showMessageDialog(frame, enemy.getName() + " casts " + enemy.selectSpell(2).getName() + "!");
            return enemy.selectSpell(2);
        } else {
            int healAmount = (int)(Math.random() * 7 + 3);
            enemy.heal(healAmount);
            System.out.println(enemy.getName() + " takes a moment to recover and has healed " + healAmount
                    + " hit points.");
            JOptionPane.showMessageDialog(frame, enemy.getName() + " takes a moment to recover and has healed "
                    + healAmount + " hit points.");
            fightPanel.removeAll();
            fightMenu();
            fightPanel.revalidate();
            fightPanel.repaint();
            return null;
        }

    }

    // Creates the battle result menu that displays the results of each turn
    // MODIFIES: this
    // EFFECTS: creates the panels for the battle result menu
    private void battleResultMenuInitializer() {
        battleResultPanel = new JPanel();
        battleResultPanel.setLayout(new BoxLayout(battleResultPanel, BoxLayout.Y_AXIS));
        battleResultPanel.setSize(0, 0);
        frame.add(battleResultPanel, BorderLayout.SOUTH);
    }

    // Pits the player's Spell against the enemy's Spell and decides how much damage is done
    // MODIFIES: this
    // EFFECTS: subtracts the appropriate amount of health from the player or the enemy if there are no critical hits
    //              Displays the results in the battle result menu.
    private void damageCalculator(Spell playerSpell, Spell enemySpell) {
        boolean playerCrit;
        boolean enemyCrit;
        battleResultMenuInitializer();
        if (playerSpell == null && enemySpell == null) {
            return; // No damage is dealt if neither the player nor the enemy cast a spell
        } else if (playerSpell == null && enemySpell != null) {
            System.out.println("You've taken " + enemySpell.getDamage() + " damage!");
            //JOptionPane.showMessageDialog(frame, "You've taken " + enemySpell.getDamage() + " damage!");
            label = new JLabel("You've taken " + enemySpell.getDamage() + " damage!");
            player.takeDamage(enemySpell.getDamage());
        } else if (playerSpell != null && enemySpell == null) {
            System.out.println(enemy.getName() + " has taken " + playerSpell.getDamage() + " damage!");
            label = new JLabel(enemy.getName() + " has taken " + playerSpell.getDamage() + " damage!");
            //JOptionPane.showMessageDialog(frame, enemy.getName() + " has taken " + playerSpell.getDamage()
            //        + " damage!");
            enemy.takeDamage(playerSpell.getDamage());
        } else if (playerSpell != null && enemySpell != null) {
            playerCrit = critCheck(playerSpell, enemySpell);
            enemyCrit = critCheck(enemySpell, playerSpell);
            critDamageCalculator(playerSpell, enemySpell, playerCrit, enemyCrit);
        }
        battleResultMenu();
        switchPanel(battleResultPanel, castSpellPanel);
        endCombatCheck();
    }

    // Checks if the player or the enemy lands a critical hit
    // REQUIRES: two Spells (cannot be null)
    // EFFECTS: return true if a critical hit is detected; return false if not
    private boolean critCheck(Spell spellOne, Spell spellTwo) {
        if (spellOne.getType().equals("Fire") && spellTwo.getType().equals("Wind")) {
            return true;
        } else if (spellOne.getType().equals("Wind") && spellTwo.getType().equals("Lightning")) {
            return true;
        } else if (spellOne.getType().equals("Lightning") && spellTwo.getType().equals("Earth")) {
            return true;
        } else if (spellOne.getType().equals("Earth") && spellTwo.getType().equals("Water")) {
            return true;
        } else if (spellOne.getType().equals("Water") && spellTwo.getType().equals("Fire")) {
            return true;
        } else {
            return false;
        }
    }

    // Calculates critical hit damage
    // REQUIRES: 2 Spells, 2 booleans (cannot be null)
    // MODIFIES: this
    // EFFECTS: subtracts the appropriate amount of health from the player or the enemy if there is a critical hit
    private void critDamageCalculator(Spell playerSpell, Spell enemySpell, boolean playerCrit, boolean enemyCrit) {
        if (playerCrit) {
            //System.out.print("Critical hit! " + enemy.getName());
            //System.out.println(" has taken " + playerSpell.getDamage() * 2 + " damage!");
            label = new JLabel("Critical hit! " + enemy.getName() + " has taken " + playerSpell.getDamage() * 2
                    + " damage!");
            enemy.takeDamage(playerSpell.getDamage() * 2);
        } else if (enemyCrit) {
            //System.out.println("Critical hit! You've taken " + enemySpell.getDamage() * 2 + " damage!");
            label = new JLabel("Critical hit! You've taken " + enemySpell.getDamage() * 2 + " damage!");
            player.takeDamage(enemySpell.getDamage() * 2);
        } else {
            //System.out.println(enemy.getName() + " has taken " + playerSpell.getDamage() + " damage!");
            enemy.takeDamage(playerSpell.getDamage());
            //System.out.println("You've taken " + enemySpell.getDamage() + " damage!");
            label = new JLabel(enemy.getName() + " has taken " + playerSpell.getDamage() + " damage! You've taken "
                    + enemySpell.getDamage() + " damage!");
            player.takeDamage(enemySpell.getDamage());
        }
    }

    // Populates the battle result menu that displays the results of each turn
    // MODIFIES: this
    // EFFECTS: adds the panels and buttons for the battle result menu
    private void battleResultMenu() {
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        battleResultPanel.add(label);
        JButton okButton = new JButton("Ok");
        battleResultPanel.add(okButton);
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.setActionCommand("0");
        okButton.addActionListener(new BattleResultMenuClickHandler());
        pack();
        setLocationRelativeTo(null);
    }

    // Check if end-of-combat conditions are met
    // MODIFIES: this
    // EFFECTS: increases the player's kill count if the enemy is defeated; ends the game if the player is defeated
    private void endCombatCheck() {
        if (player.getCurrentHealth() <= 0) {
            System.out.println("\nYou have been slain by " + enemy.getName());
            System.out.println("You killed " + player.getEnemiesKilled() + " enemies. \nGame Over!");
            JOptionPane.showMessageDialog(frame, "You have been slain by " + enemy.getName() + "\nYou killed "
                    + player.getEnemiesKilled() + " enemies. \nGame Over!");
            System.exit(0);
        } else if (enemy.getCurrentHealth() <= 0) {
            System.out.println("\nYou have slain " + enemy.getName() + "!");
            JOptionPane.showMessageDialog(frame, "You have slain " + enemy.getName() + "!");
            player.setEnemiesKilled(player.getEnemiesKilled() + 1);
            enemyAlive = false;
            preFightMenu();
            switchPanel(preFightPanel, battleResultPanel);
        } else {
            fightPanel.removeAll();
            //fightMenu();
            //switchPanel(fightPanel, battleResultPanel);
            //fightPanel.removeAll();

            //fightMenu();
            //fightPanel.revalidate();
            //fightPanel.repaint();
        }
    }

    // Method to save the game state to a json file
    // MODIFIES: this
    // EFFECTS: saves game to json file
    private void saveGame() {
        try {
            saveGameWriter.open();
            saveGameWriter.write(player);
            saveGameWriter.close();
            System.out.println("Saved " + player.getName() + " to " + JSON_STORE);
            JOptionPane.showMessageDialog(frame, "Saved " + player.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // Method to load the game state from a json file
    // MODIFIES: this
    // EFFECTS: reads saved game from json file
    private void loadGame() {
        try {
            player = saveGameReader.read();
            System.out.println("Loaded " + player.getName() + " from " + JSON_STORE);
            JOptionPane.showMessageDialog(frame, "Loaded " + player.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Save game: " + JSON_STORE + " not found!");
        }
        learnedSpellRemover();
        preFightMenu();
        switchPanel(preFightPanel, startButtonPanel);
    }

    // Helper method to remove the Spells the player has already learned from the available Spell pool when loading
    //      a game
    // MODIFIES: this
    // EFFECTS: compares the player's spell list to the overall spell pool and removes any duplicates from the pool
    private void learnedSpellRemover() {
        for (Spell spell : player.getSpellList()) {
            for (int count = 0; count < mageSpells.size(); count++) {
                if (spell.getName().equals(mageSpells.get(count).getName())) {
                    mageSpells.remove(count);
                    count--;
                }
            }
        }
    }

    // Helper method to switch JPanels
    // MODIFIES: this
    // EFFECTS: makes oldPanel invisible and newPanel visible
    private void switchPanel(JPanel newPanel, JPanel oldPanel) {
        //oldPanel.setVisible(false);
        oldPanel.removeAll();
        frame.setContentPane(newPanel);
        frame.revalidate();
    }

    // click handlers for all the menus in the game
    protected class StartScreenClickHandler implements ActionListener {

        // EFFECTS: when button is clicked, sends corresponding command
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Start Game")) {
                processStartScreenCommand(1);
            } else if (e.getActionCommand().equals("Load Game")) {
                processStartScreenCommand(2);
            } else if (e.getActionCommand().equals("Continue")) {
                processNameMenu(nameField.getText());
            }
        }
    }

    protected class SelectSpellClickHandler implements ActionListener {

        // EFFECTS: when button is clicked, sends corresponding command
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("1")) {
                processSpellSelectCommand(1);
            } else if (e.getActionCommand().equals("2")) {
                processSpellSelectCommand(2);
            } else if (e.getActionCommand().equals("3")) {
                processSpellSelectCommand(3);
            } else if (e.getActionCommand().equals("4")) {
                processSpellSelectCommand(4);
            } else if (e.getActionCommand().equals("5")) {
                processSpellSelectCommand(5);
            }
        }
    }

    protected class PreFightMenuClickHandler implements ActionListener {

        // EFFECTS: when button is clicked, sends corresponding command
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("View Spell list")) {
                processPreFightCommand(1);
            } else if (e.getActionCommand().equals("Fight!")) {
                //processPreFightCommand(2);
                startFight();
            } else if (e.getActionCommand().equals("Rest")) {
                processPreFightCommand(3);
            } else if (e.getActionCommand().equals("Learn new Spell")) {
                processPreFightCommand(4);
            } else if (e.getActionCommand().equals("Upgrade Spell")) {
                processPreFightCommand(5);
            } else if (e.getActionCommand().equals("Save Game")) {
                //processPreFightCommand(9);
                saveGame();
            }
        }
    }

    protected class LearnSpellClickHandler implements ActionListener {

        // EFFECTS: when button is clicked, sends corresponding command
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("1")) {
                processLearnSpellMenuCommand(1);
            } else if (e.getActionCommand().equals("2")) {
                processLearnSpellMenuCommand(2);
            } else if (e.getActionCommand().equals("3")) {
                processLearnSpellMenuCommand(3);
            } else if (e.getActionCommand().equals("4")) {
                processLearnSpellMenuCommand(4);
            } else if (e.getActionCommand().equals("5")) {
                processLearnSpellMenuCommand(5);
            } else if (e.getActionCommand().equals("0")) {
                processLearnSpellMenuCommand(0);
            }
        }
    }

    protected class LevelUpClickHandler implements ActionListener {

        // EFFECTS: when button is clicked, sends corresponding command
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("1")) {
                processLevelUpMenuCommand(1);
            } else if (e.getActionCommand().equals("2")) {
                processLevelUpMenuCommand(2);
            } else if (e.getActionCommand().equals("3")) {
                processLevelUpMenuCommand(3);
            } else if (e.getActionCommand().equals("0")) {
                processLevelUpMenuCommand(0);
            }
        }
    }

    protected class FightMenuClickHandler implements ActionListener {

        // EFFECTS: when button is clicked, sends corresponding command
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Cast a Spell")) {
                processCombatMenuCommand(1);
            } else if (e.getActionCommand().equals("Recover")) {
                processCombatMenuCommand(2);
            }
        }
    }

    protected class CastSpellClickHandler implements ActionListener {

        // EFFECTS: when button is clicked, sends corresponding command
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("1")) {
                processCastSpellCommand(1);
            } else if (e.getActionCommand().equals("2")) {
                processCastSpellCommand(2);
            } else if (e.getActionCommand().equals("3")) {
                processCastSpellCommand(3);
            } else if (e.getActionCommand().equals("0")) {
                processCastSpellCommand(0);
            }
        }
    }

    protected class BattleResultMenuClickHandler implements ActionListener {

        // EFFECTS: switches to fight menu when button is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("0")) {
                fightMenu();
                switchPanel(fightPanel, battleResultPanel);
            }
        }
    }
}
