/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pixelshop;

/**
 *
 * @author USER
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.*;

public class MiniRPG extends JPanel implements ActionListener, KeyListener {

    private static final double SCALE = 1.25; // ganti ke 2.0 kalau mau 2x, dst

    // Player properties
    int playerX = 75, playerY = 30;
    int speed = 6;
    String facing = "down";

    boolean up, down, left, right;

    // Game state
    boolean inDialogue = false;
    String dialogueText = "";

    // Walls
    ArrayList<Rectangle> walls = new ArrayList<>();
    ArrayList<Rectangle> hiddenWalls = new ArrayList<>();

    // NPCs
    Rectangle npc = new Rectangle(200, 200, 80, 80);
    int dialogueStep = 0;

    Timer timer;
    Image playerImage;
    Image npcImageIdle;
    Image npcImageTalking;
    Image currentNpcImage;
    Image npcImageIntruder;
    Image npcImageAwww;
    Image npcImageMischevious;
    ImageIcon splatSprite;
    ImageIcon peaceSprite;
    Rectangle portalWall = new Rectangle(50, 0, 80, 20); // sesuai gambar portal
    boolean portalUsed = false; // flag
    // NPC jump variables
    boolean npcJumping = false;
    int npcYOriginal;    // Original Y position
    int npcYOffset = 0;  // How much to move up/down (start at 0)
    int npcJumpSpeed = -6; // Negative = upward jump
    int npcVelocity = 0;  // Will be set when jumping
    String fullDialogue = "";      // the full line to type out
    String visibleDialogue = "";   // the part that's shown on screen
    Timer typingTimer;
    boolean typing = false;
    boolean npcShaking = false;
    int npcShakeOffsetX = 0;
    int npcShakeCounter = 0;
    boolean npcFacingLeft = true; // default: facing left
    int npcInteractionCount = 0; // track how many times player interacted
    Rectangle hiddenObject = new Rectangle(300, 200, 64, 64); // posisi x=300 y=200 ukuran 64x64
    Image hiddenObjectImageClosed;
    Image hiddenObjectImageOpen;
    boolean objectOpened = false;
    boolean inHiddenDialogue = false;   // khusus hidden room
    String hiddenFullDialogue = "";
    String hiddenVisibleDialogue = "";
    boolean hiddenTyping = false;
    Timer hiddenTypingTimer;
    boolean showExitAnimation = false;
    float exitAlpha = 0.0f; // buat fade in
    int exitX, exitY;       // posisi munculnya karakter
    ImageIcon exitGif;      // gambar GIF
    int exitTargetY;   // posisi akhir (keluar chest)
    boolean exitFinished = false;
    float velY;        // kecepatan vertikal
    float gravity = 1; // gaya gravitasi
    float velX;        // kecepatan horizontal
    int groundY;       // posisi tanah (tinggi jatuh)
    int groundX;       // posisi mendarat
    boolean fadingOut = false;
    boolean splatTriggered = false;
    boolean inCutscene = false;
    // add these as fields:
    boolean specialDialogue1Triggered = false;
    boolean specialDialogue2Triggered = false;

    public MiniRPG() {
        setPreferredSize(new Dimension((int) (400 * SCALE), (int) (400 * SCALE)));
        AudioPlayer.getInstance().playMiniRPGMusic();

        setBackground(Color.BLACK);

        playerImage = new ImageIcon(getClass().getResource("/pixelshop/player.png")).getImage();
        npcImageIdle = new ImageIcon(getClass().getResource("/pixelshop/finalboss.png")).getImage();
        npcImageTalking = new ImageIcon(getClass().getResource("/pixelshop/OMAI.png")).getImage();
        npcImageIntruder = new ImageIcon(getClass().getResource("/pixelshop/INTRUDER!.png")).getImage();
        npcImageMischevious = new ImageIcon(getClass().getResource("/pixelshop/hehe.png")).getImage();
        npcImageAwww = new ImageIcon(getClass().getResource("/pixelshop/awww.png")).getImage();
        hiddenObjectImageClosed = new ImageIcon(getClass().getResource("/pixelshop/treasure_closed.png")).getImage();
        hiddenObjectImageOpen = new ImageIcon(getClass().getResource("/pixelshop/treasure_open.png")).getImage();
        currentNpcImage = npcImageIdle; // start idle

        // Example walls
        walls.add(new Rectangle(120, 50, 210, 5));  // Top horizontal wall
        walls.add(new Rectangle(50, 330, 280, 5)); // Bottom horizontal wall
        walls.add(new Rectangle(50, 50, 5, 280));  // Left vertical wall
        walls.add(new Rectangle(330, 50, 5, 285)); // Right vertical wall

        timer = new Timer(16, this); // ~60 FPS
        timer.start();

        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!inDialogue) {
            int nextX = playerX;
            int nextY = playerY;

            if (up) {
                nextY -= speed;
                facing = "up";
            }
            if (down) {
                nextY += speed;
                facing = "down";
            }
            if (left) {
                nextX -= speed;
                facing = "left";
            }
            if (right) {
                nextX += speed;
                facing = "right";
            }

            Rectangle nextRect = new Rectangle(nextX, nextY, 32, 32);

            if (!isColliding(nextX, nextY)) {
                playerX = nextX;
                playerY = nextY;
            }
        }

        // NPC jump physics
        if (npcJumping) {
            npcYOffset += npcVelocity;
            npcVelocity += 1; // gravity

            if (npcYOffset >= 0) { // landed
                npcYOffset = 0;
                npcJumping = false;
            }
        }

        if (npcShaking) {
            npcShakeCounter++;
            // Alternate between -3 and +3 pixels every frame
            npcShakeOffsetX = (npcShakeCounter % 4 < 2) ? -3 : 3;

            // Stop after 20 frames (~0.33 sec at 60 FPS)
            if (npcShakeCounter > 20) {
                npcShaking = false;
                npcShakeOffsetX = 0;
            }
        }

        repaint();
    }

    public boolean isColliding(int x, int y) {
        Rectangle playerRect = new Rectangle(x, y, 15, 15);
        for (Rectangle wall : walls) {
            if (playerRect.intersects(wall)) {
                return true;
            }
        }
        for (Rectangle wall : hiddenWalls) {
            if (playerRect.intersects(wall)) {
                return true;
            }
        }
        return false;
    }

    public void interact() {
        Rectangle interactZone = null;
        if (facing.equals("up")) {
            interactZone = new Rectangle(playerX, playerY - 32, 32, 12);
        }
        if (facing.equals("down")) {
            interactZone = new Rectangle(playerX, playerY + 32, 32, 12);
        }
        if (facing.equals("left")) {
            interactZone = new Rectangle(playerX - 32, playerY, 5, 32);
        }
        if (facing.equals("right")) {
            interactZone = new Rectangle(playerX + 32, playerY, 5, 32);
        }

        if (interactZone.intersects(npc)) {
            inDialogue = true;
            inHiddenDialogue = false; // make sure hidden dialog is off

            // FIRST special dialogue
            if (objectOpened) {
                // FIRST special dialogue
                if (npcInteractionCount < 8 && !specialDialogue1Triggered && !specialDialogue2Triggered) {
                    specialDialogue1Triggered = true;
                    startTyping("HOW DO YOU KNOW?? I never told you any hidden room before...");
                    currentNpcImage = npcImageTalking;
                    if (!npcJumping) {
                        npcJumping = true;
                        npcVelocity = npcJumpSpeed;
                    }
                    return; // stop here, don't increment
                }

                // SECOND special dialogue
                if (npcInteractionCount > 8 && !specialDialogue2Triggered && !specialDialogue1Triggered) {
                    specialDialogue2Triggered = true;
                    startTyping("hehe, anomali");
                    currentNpcImage = npcImageMischevious;
                    npcShaking = false;
                    npcYOffset = 0;
                    return; // stop here, don't increment
                }
            }

            // kalau bukan kondisi khusus -> increment normal
            npcInteractionCount++;

            if (playerX < npc.x) {
                npcFacingLeft = true;
            } else {
                npcFacingLeft = false;
            }
            inDialogue = true;
            dialogueStep = 1;

            if (npcInteractionCount == 1) {
                startTyping("EH? HUH? WHAT? WHO? HOW? HUH??????");
                currentNpcImage = npcImageTalking;
                if (!npcJumping) {
                    npcJumping = true;
                    npcVelocity = npcJumpSpeed;
                }
            } else if (npcInteractionCount == 2) {
                startTyping("HEY! YOU AGAIN? DIDN'T I TELL YOU TO LEAVE?!");
                currentNpcImage = npcImageIntruder;
                startNpcShake();
                if (!npcJumping) {
                    npcJumping = true;
                    npcVelocity = npcJumpSpeed;
                }
            } else if (npcInteractionCount == 8) {
                startTyping("Stop it, do not expect more hidden dialogues");
                currentNpcImage = npcImageIntruder;
                startNpcShake();
                if (!npcJumping) {
                    npcJumping = true;
                    npcVelocity = npcJumpSpeed;
                }
            } else if (npcInteractionCount == 9 && !objectOpened) {
                startTyping("maybe expect a hidden room?");
                currentNpcImage = npcImageMischevious;
                npcShaking = false;
                npcYOffset = 0;
            } else if (npcInteractionCount == 10) {
                startTyping("Press Z on EXIT to go back.");
                currentNpcImage = npcImageIdle;
                npcShaking = false;
                npcYOffset = 0;
            } else if (npcInteractionCount == 13) {
                startTyping("I have nothing...");
                currentNpcImage = npcImageIdle;
                npcShaking = false;
                npcYOffset = 0;
            } else if (npcInteractionCount == 14) {
                startTyping("I HAVE SO MUCH TO SAY!");
                currentNpcImage = npcImageAwww;
                npcShaking = false;
                npcYOffset = 0;
            } else if (npcInteractionCount == 15) {
                startTyping("This sprite is drawn using Microsoft Paint!");
                currentNpcImage = npcImageIdle;
                npcShaking = false;
                npcYOffset = 0;
            } else if (npcInteractionCount == 16) {
                startTyping("E-EH? SOUNDTRACK COPYRIGHT?");
                currentNpcImage = npcImageTalking;
                if (!npcJumping) {
                    npcJumping = true;
                    npcVelocity = npcJumpSpeed;
                }
            } else if (npcInteractionCount == 18 && !objectOpened) {
                startTyping("Don't forget to check any hidden room here! use your sight!");
                currentNpcImage = npcImageMischevious;
                npcShaking = false;
                npcYOffset = 0;
            } else {
                startTyping("hmph, I have nothing more to say");
                currentNpcImage = npcImageIdle;
                npcShaking = false;
                npcYOffset = 0;
            }

//            currentNpcImage = npcImageTalking;
//
//            if (!npcJumping) {
//                npcJumping = true;
//                npcVelocity = npcJumpSpeed;
//            }
        }

        // Interact with hidden object
        if (interactZone.intersects(hiddenObject)) {
            inHiddenDialogue = true;
            inDialogue = false; // pastikan NPC dialog mati
            if (!objectOpened && !inCutscene) {   // <- tambah cek ini
                inCutscene = true;               // lock interaksi
                objectOpened = true;
                inHiddenDialogue = true;
                startHiddenTyping("You opened the chest! Something is inside...");

                showExitAnimation = true;
                exitAlpha = 0.0f;

// mulai dari chest
                exitX = hiddenObject.x + 340;
                exitY = hiddenObject.y + 100;

// target tanah agak ke kanan
                groundX = exitX + 50;     // 50px ke samping chest
                groundY = exitY + 70;     // tanah lebih bawah
                velX = 2;                 // maju pelan
                velY = -12;               // lompat ke atas
                exitFinished = false;

                exitGif = new ImageIcon(getClass().getResource("/pixelshop/kris_battle_twirling.gif"));

// Timer animasi: lompat parabola + maju
                new Timer(50, e -> {
                    if (!exitFinished) {
                        exitAlpha = Math.min(1.0f, exitAlpha + 0.05f); // fade in

                        // update posisi
                        exitX += velX;
                        velY += gravity;
                        exitY += velY;

                        // cek sudah sampai tanah
                        if (exitY >= groundY && exitX >= groundX) {
                            exitY = groundY;
                            exitFinished = true;
                        }
                        repaint();
                    }// cek sudah sampai tanah
                    if (!splatTriggered && exitY >= groundY && exitX >= groundX) {
                        splatTriggered = true;   // supaya hanya sekali jalan
                        exitY = groundY;
                        exitFinished = true;

                        // ganti sprite jadi splat
                        splatSprite = new ImageIcon(getClass().getResource("/pixelshop/Kris_overworld_splat.png"));
                        exitGif = splatSprite;

                        // dialog "Oh..?"
                        inHiddenDialogue = true;
                        startHiddenTyping("Oh..?");

                        // setelah 2 detik -> dialog baru
                        new Timer(2000, ev1 -> {
                            currentNpcImage = npcImageMischevious;
                            startHiddenTyping("Hello? Who's this?");

                            // setelah dialog kedua selesai, ganti peace gesture + mulai fade out
                            new Timer(2000, ev2 -> {
                                currentNpcImage = npcImageIdle;
                                peaceSprite = new ImageIcon(getClass().getResource("/pixelshop/Kris_overworld_peace_gesture.png"));
                                exitGif = peaceSprite;
                                fadingOut = true;
                                startHiddenTyping("erm, okay? good bye...");
                                new Timer(100, ev3 -> {
                                    if (fadingOut) {
                                        exitAlpha = Math.max(0f, exitAlpha - 0.05f);
                                        if (exitAlpha <= 0f) {
                                            fadingOut = false;
                                            ((Timer) ev3.getSource()).stop();
                                        }
                                        repaint();
                                    }
                                    if (exitAlpha <= 0f) {
                                        fadingOut = false;
                                        showExitAnimation = false;  // sprite Kris udah ga digambar
                                        inCutscene = false;         // unlock interaksi chest lagi
                                        ((Timer) ev3.getSource()).stop();
                                    }

                                }).start();

                                ((Timer) ev2.getSource()).stop();
                            }).start();

                            ((Timer) ev1.getSource()).stop();
                        }).start();
                    }

                }).start();
            } else if (objectOpened && !inCutscene) {
                // chest sudah dibuka, tapi TIDAK sedang cutscene
                inHiddenDialogue = true;
                startHiddenTyping("The chest is already open, it's empty.");
            }
        }

    }

    @Override
    public void keyPressed(KeyEvent e
    ) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) {
            up = true;
        }
        if (code == KeyEvent.VK_DOWN) {
            down = true;
        }
        if (code == KeyEvent.VK_LEFT) {
            left = true;
        }
        if (code == KeyEvent.VK_RIGHT) {
            right = true;
        }

        if (code == KeyEvent.VK_SHIFT) {
            speed = 12; // 2x lipat dari default 6
        }

        if (code == KeyEvent.VK_Z) {
            if (!inDialogue) {
                interact();
                checkPortal();
            } else {
                if (inHiddenDialogue) {
                    if (hiddenTyping) {
                        hiddenVisibleDialogue = hiddenFullDialogue;
                        hiddenTyping = false;
                        hiddenTypingTimer.stop();
                        repaint();
                    } else {
                        inHiddenDialogue = false;
                        hiddenVisibleDialogue = "";
                    }
                    return; // supaya ga lanjut ke NPC
                }

                if (typing) {
                    visibleDialogue = fullDialogue;
                    typing = false;
                    typingTimer.stop();
                    repaint();
                } else {
                    dialogueStep++;

                    switch (npcInteractionCount) {
                        case 1: // first interaction sequence
                            switch (dialogueStep) {
                                case 2:
                                    startTyping("HUH?");
                                    break;
                                case 3:
                                    startTyping("HOW DID YOU FIND ME???");
                                    currentNpcImage = npcImageIntruder;
                                    startNpcShake();
                                    break;
                                case 4:
                                    startTyping("YOU SNAPPED THE STRINGS???");
                                    currentNpcImage = npcImageTalking;
                                    if (!npcJumping) {
                                        npcJumping = true;
                                        npcVelocity = npcJumpSpeed;
                                    }
                                    break;
                                case 5:
                                    startTyping("THAT'S UNFAIR!! GET OUT! THIS PART IS NOT FINISHED YET!");
                                    currentNpcImage = npcImageIntruder;
                                    startNpcShake();
                                    break;
                                default:
                                    inDialogue = false;
                                    dialogueStep = 0;
                                    currentNpcImage = npcImageIdle;
                                    visibleDialogue = "";
                                    break;
                            }
                            break;

                        case 2: // second interaction sequence
                            switch (dialogueStep) {
                                case 2:
                                    startTyping("HEY! YOU AGAIN? DIDN'T I TELL YOU TO LEAVE?!");
                                    currentNpcImage = npcImageIntruder;
                                    startNpcShake();
                                    break;
                                case 3:
                                    startTyping("PLEASE LET ME CODE IN PEACE!");
                                    currentNpcImage = npcImageIntruder;
                                    startNpcShake();
                                    break;
                                case 4:
                                    startTyping("BEGONE BEFORE I BITE YOU!");
                                    currentNpcImage = npcImageIntruder;
                                    startNpcShake();
                                    break;
                                default:
                                    inDialogue = false;
                                    dialogueStep = 0;
                                    currentNpcImage = npcImageIdle;
                                    visibleDialogue = "";
                                    break;
                            }
                            break;

                        case 14: // second interaction sequence
                            switch (dialogueStep) {
                                case 2:
                                    startTyping("Do you really want to accompany me here?");
                                    currentNpcImage = npcImageAwww;
                                    startNpcShake();
                                    break;
                                case 3:
                                    startTyping("IT'S SO LONELY AND EMPTY HERE T T");
                                    currentNpcImage = npcImageAwww;
                                    startNpcShake();
                                    break;
                                case 4:
                                    startTyping("FINE THEN. If you interact more, i'll tell you some fun facts..");
                                    currentNpcImage = npcImageMischevious;
                                    break;
                                default:
                                    inDialogue = false;
                                    dialogueStep = 0;
                                    currentNpcImage = npcImageIdle;
                                    visibleDialogue = "";
                                    break;
                            }
                            break;

                        case 15: // second interaction sequence
                            switch (dialogueStep) {
                                case 2:
                                    startTyping("I COULD'VE used PixelStudio,..");
                                    currentNpcImage = npcImageAwww;
                                    startNpcShake();
                                    break;
                                case 3:
                                    startTyping("But that's on mobile AND i'm in office!");
                                    currentNpcImage = npcImageAwww;
                                    startNpcShake();
                                    break;
                                case 4:
                                    startTyping("So I don't want my co-workers go:");
                                    currentNpcImage = npcImageAwww;
                                    startNpcShake();
                                    break;
                                case 5:
                                    startTyping("DON'T SLACK OFF, LIL CHILD!");
                                    currentNpcImage = npcImageIntruder;
                                    if (!npcJumping) {
                                        npcJumping = true;
                                        npcVelocity = npcJumpSpeed;
                                    }
                                    break;
                                case 6:
                                    startTyping("Got it??");
                                    currentNpcImage = npcImageAwww;
                                    startNpcShake();
                                    break;
                                default:
                                    inDialogue = false;
                                    dialogueStep = 0;
                                    currentNpcImage = npcImageIdle;
                                    visibleDialogue = "";
                                    break;
                            }
                            break;
                        case 16: // second interaction sequence
                            switch (dialogueStep) {
                                case 2:
                                    startTyping("There should'nt have been any..");
                                    currentNpcImage = npcImageIdle;
                                    break;
                                case 3:
                                    startTyping("Those are original soundtracks afterall.");
                                    currentNpcImage = npcImageMischevious;
                                    break;
                                case 4:
                                    startTyping("The current OST should be safe.");
                                    currentNpcImage = npcImageIdle;
                                    break;
                                case 5:
                                    startTyping("Since I used FL Studio Mobile..");
                                    currentNpcImage = npcImageMischevious;
                                    break;
                                case 6:
                                    startTyping("But the Main OST...");
                                    currentNpcImage = npcImageIdle;
                                    break;
                                case 7:
                                    startTyping("I mix samples with BandLab!");
                                    currentNpcImage = npcImageMischevious;
                                    break;
                                default:
                                    inDialogue = false;
                                    dialogueStep = 0;
                                    currentNpcImage = npcImageIdle;
                                    visibleDialogue = "";
                                    break;
                            }
                            break;

                        default: // third+ interactions
                            startTyping("I have nothing more to say...");
                            inDialogue = false;
                            dialogueStep = 0;
                            currentNpcImage = npcImageIdle;  // back to idle
                            npcShaking = false;              // stop shaking
                            npcYOffset = 0;                  // reset jump offset
                            break;

                    }
                }
            }
        }

    }

    public void startNpcShake() {
        npcShaking = true;
        npcShakeCounter = 0;
    }

    public void startTyping(String text) {
        fullDialogue = text;
        visibleDialogue = "";
        typing = true;

        if (typingTimer != null && typingTimer.isRunning()) {
            typingTimer.stop();
        }

        typingTimer = new Timer(30, e -> {
            if (visibleDialogue.length() < fullDialogue.length()) {
                visibleDialogue += fullDialogue.charAt(visibleDialogue.length());

                // Play blip every 2 characters (log instead)
                if (visibleDialogue.length() % 2 == 0) {
                    System.out.println("Blip! char #" + visibleDialogue.length());
                    // playBlipSound(); // commented out for now
                }

            } else {
                typing = false;
                typingTimer.stop();
            }
            repaint();
        });
        typingTimer.start();
    }

    public void startHiddenTyping(String text) {
        hiddenFullDialogue = text;
        hiddenVisibleDialogue = "";
        hiddenTyping = true;

        if (hiddenTypingTimer != null && hiddenTypingTimer.isRunning()) {
            hiddenTypingTimer.stop();
        }

        hiddenTypingTimer = new Timer(30, e -> {
            if (hiddenVisibleDialogue.length() < hiddenFullDialogue.length()) {
                hiddenVisibleDialogue += hiddenFullDialogue.charAt(hiddenVisibleDialogue.length());

                // Play blip every 2 characters (log instead)
                if (hiddenVisibleDialogue.length() % 2 == 0) {
                    System.out.println("Blip! hidden char #" + hiddenVisibleDialogue.length());
                    // playBlipSound(); // commented out for now
                }

            } else {
                hiddenTyping = false;
                hiddenTypingTimer.stop();
            }
            repaint();
        });
        hiddenTypingTimer.start();
    }

    public void checkPortal() {
        Rectangle playerRect = new Rectangle(playerX, playerY, 32, 32);
        if (playerRect.intersects(portalWall)) {
            if (!portalUsed) {
                portalUsed = true; // tandai sudah dipakai
                openNewPage();
            }
        } else {
            portalUsed = false; // reset kalau keluar portal
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) {
            up = false;
        }
        if (code == KeyEvent.VK_DOWN) {
            down = false;
        }
        if (code == KeyEvent.VK_LEFT) {
            left = false;
        }
        if (code == KeyEvent.VK_RIGHT) {
            right = false;
        }

        if (code == KeyEvent.VK_SHIFT) {
            speed = 6; // balik ke normal
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Pakai Graphics2D baru supaya transform tidak "mengotori" g asli
        Graphics2D g2 = (Graphics2D) g.create();

        // Interpolasi NEAREST_NEIGHBOR supaya pixel art tetap tajam saat dibesarkan
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

        // Skala seluruh kanvas (semua yang tergambar setelah ini otomatis 1.5x)
        g2.scale(SCALE, SCALE);

        // ==== Mulai gambar pakai g2 di "koordinat logis" 400x400 ====
        int logicalWidth = (int) Math.round(getWidth() / SCALE);
        int logicalHeight = (int) Math.round(getHeight() / SCALE);

        // Latar ruangan utama
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 400, 400);

        // Ruangan tersembunyi (pakai ukuran logis panel)
        if (logicalWidth > 400 && logicalHeight > 400) {
            int hiddenX = 1150;
            int hiddenY = 420;
            int hiddenWidth = logicalWidth - 400;
            int hiddenHeight = logicalHeight - 400;

            g2.setColor(Color.BLACK);
            g2.drawString("Hidden Room", hiddenX + 20, hiddenY + 30);

            // Posisi objek tersembunyi
            int objX = 1320;
            int objY = 600;

            if (hiddenObject == null) {
                hiddenObject = new Rectangle(objX, objY, 64, 64);
            } else {
                hiddenObject.setBounds(objX, objY, 64, 64);
            }

            if (objectOpened) {
                g2.drawImage(hiddenObjectImageOpen, hiddenObject.x, hiddenObject.y,
                        hiddenObject.width, hiddenObject.height, this);
            } else {
                g2.drawImage(hiddenObjectImageClosed, hiddenObject.x, hiddenObject.y,
                        hiddenObject.width, hiddenObject.height, this);
            }

            // Dinding ruangan tersembunyi
            hiddenWalls.clear();
            hiddenWalls.add(new Rectangle(hiddenX + 120, hiddenY + 50, 210, 5));
            hiddenWalls.add(new Rectangle(hiddenX + 50, hiddenY + 330, 280, 5));
            hiddenWalls.add(new Rectangle(hiddenX + 50, hiddenY + 50, 5, 280));
            hiddenWalls.add(new Rectangle(hiddenX + 330, hiddenY + 50, 5, 285));

            g2.setColor(Color.WHITE);
            for (Rectangle wall : hiddenWalls) {
                g2.fillRect(wall.x, wall.y, wall.width, wall.height);
            }
        }

        // Font kustom (ukuran logis 16pt -> tampak 24pt karena diskalakan 1.5x)
        try {
            Font Text = Font.createFont(Font.TRUETYPE_FONT,
                    new File("src/fonts/DeterminationSansWebRegular-369X.ttf"))
                    .deriveFont(16f);
            g2.setFont(Text);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            g2.setFont(new Font("Arial", Font.PLAIN, 16)); // fallback
        }

        // Portal + tulisan "Exit"
        g2.setColor(Color.BLACK);
        g2.fillRect(portalWall.x, portalWall.y, portalWall.width, portalWall.height);

        g2.setColor(Color.WHITE);
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth("Exit");
        int textX = portalWall.x + (portalWall.width - textWidth) / 2;
        int textY = portalWall.y + ((portalWall.height - fm.getHeight()) / 2) + fm.getAscent();
        g2.drawString("Exit", textX, textY);

        // Dinding ruangan utama
        g2.setColor(Color.WHITE);
        for (Rectangle wall : walls) {
            g2.fillRect(wall.x, wall.y, wall.width, wall.height);
        }

        if (showExitAnimation && exitGif != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            float alpha = Math.max(0f, Math.min(1f, exitAlpha));
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            exitGif.paintIcon(this, g2d, exitX, exitY);
            g2d.dispose();
        }

        // NPC (ikut goyang/jump & flip seperti semula)
        if (npcFacingLeft) {
            g2.drawImage(currentNpcImage,
                    npc.x + npcShakeOffsetX,
                    npc.y + npcYOffset,
                    npc.width, npc.height, this);
        } else {
            g2.drawImage(currentNpcImage,
                    npc.x + npc.width + npcShakeOffsetX,
                    npc.y + npcYOffset,
                    -npc.width, npc.height, this);
        }

        if (inHiddenDialogue) {
            // Posisi hidden room (sesuai gambar)
            int hiddenX = 1150;
            int hiddenY = 420;

            // Posisi dialog box tepat di bawah hidden room
            int dialogX = hiddenX;
            int dialogY = hiddenY + 250; // 20px jarak dari bawah ruangan
            int dialogW = 360;
            int dialogH = 70;

            // Warna ungu
            g2.setColor(Color.WHITE);
            g2.fillRect(dialogX, dialogY, dialogW, dialogH);

            g2.setColor(Color.BLACK);
            g2.fillRect(dialogX + 2, dialogY + 2, dialogW - 4, dialogH - 4);

            g2.setColor(Color.WHITE);
            g2.drawRect(dialogX, dialogY, dialogW, dialogH);

            g2.setColor(Color.WHITE);
            g2.drawString(hiddenVisibleDialogue, dialogX + 10, dialogY + 30);
        }

        // Player
        g2.drawImage(playerImage, playerX, playerY, 20, 20, this);

        // Dialog box (ikut membesar)
        if (inDialogue) {
            g2.setColor(Color.WHITE);
            g2.fillRect(20, 300, 360, 70);

            g2.setColor(Color.BLACK);
            g2.fillRect(22, 302, 356, 66);

            g2.setColor(Color.WHITE);
            g2.drawRect(20, 300, 360, 70);

            g2.drawString(visibleDialogue, 30, 330);
        }

        // Bantuan kontrol di bawah
        g2.setColor(new Color(255, 255, 255, 200));
        g2.setFont(new Font("Arial", Font.PLAIN, 12)); // jadi ~18pt tampak layar
        g2.drawString("↑↓←→ : Gerak   |   Z : Interaksi/Portal  |  SHIFT : Sprint",
                10, Math.max(390, logicalHeight - 10));

        // Selesai: buang context turunan
        g2.dispose();

        // Dialog box untuk hidden room
        // Dialog box untuk hidden room
        // Dialog box untuk hidden room
    }

    public void playBlipSound() {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(
                    getClass().getResource("/pixelshop/blip.wav")
            );
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Room");
        MiniRPG game = new MiniRPG();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void openNewPage() {
        new Login().setVisible(true); // buka halaman login
        SwingUtilities.getWindowAncestor(this).dispose(); // tutup halaman ini
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 533, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 421, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
