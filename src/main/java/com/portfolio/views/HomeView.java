package com.portfolio.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Vaibhav | Fullstack Software Engineer")
public class HomeView extends Div {

    public HomeView() {
        addClassName("home-view");
        
        // Grid background
        Div gridBg = new Div();
        gridBg.addClassName("grid-bg");
        add(gridBg);

        // ==========================================
        // SECTION 1: INTRO SCREEN (#intro)
        // ==========================================
        Div introSection = new Div();
        introSection.setId("intro");
        introSection.addClassName("scroll-section");
        introSection.addClassName("intro-sec");

        Div heroWrapper = new Div();
        heroWrapper.addClassName("hero-flex");

        // Left Side: Content
        Div heroContent = new Div();
        heroContent.addClassName("hero-content");

        H1 mainTitle = new H1("BUILDING THE FUTURE OF THE OLD SCHOOL");
        mainTitle.addClassName("retro-title");

        Paragraph mainBio = new Paragraph(
            "Hello, I'm Vaibhav. I am a Backend & Systems Engineer specializing in Java, emulation, and high-concurrency microservices. " +
            "I combine low-level retro computing mechanics (like writing cycle-accurate console emulators and soft-renderers) with modern production architecture (Spring Boot, Redis, and WebSockets) " +
            "to create performant, high-quality, and immersive software."
        );
        mainBio.addClassName("retro-text");

        Div btnGroup = new Div();
        btnGroup.addClassName("hero-btn-group");

        Button viewProjects = new Button("INSPECT MY WORK");
        viewProjects.addClassName("arcade-btn");
        viewProjects.addClickListener(e -> scrollToSection("projects"));

        Button viewStats = new Button("READ BIOGRAPHY");
        viewStats.addClassName("arcade-btn");
        viewStats.addClickListener(e -> scrollToSection("about"));

        Button viewResumeBtn = new Button("VIEW RESUME");
        viewResumeBtn.addClassName("arcade-btn");
        viewResumeBtn.addClassName("pink");

        Anchor viewResume = new Anchor("https://drive.google.com/file/d/1B2fFAJUsMzyJf_mu7X1u3K2CeN0COkqv/view?usp=sharing", viewResumeBtn);
        viewResume.setTarget("_blank");
        viewResume.getStyle().set("text-decoration", "none");
        viewResume.getStyle().set("display", "inline-flex");

        btnGroup.add(viewProjects, viewStats, viewResume);
        heroContent.add(mainTitle, mainBio, btnGroup);
        heroWrapper.add(heroContent);

        // Right Side: Animation Video Preview (Popping Cat Video) & Pet Cat Interface
        Div heroGraphicWrapper = new Div();
        heroGraphicWrapper.addClassName("hero-graphic-wrapper");

        Div heroGraphic = new Div();
        heroGraphic.setId("hero-cat-graphic");
        heroGraphic.addClassName("hero-graphic");

        com.vaadin.flow.component.HtmlContainer catVideo = new com.vaadin.flow.component.HtmlContainer("video");
        catVideo.getElement().setAttribute("src", "themes/retrotheme/cat.mp4");
        catVideo.getElement().setAttribute("autoplay", true);
        catVideo.getElement().setAttribute("loop", true);
        catVideo.getElement().setAttribute("muted", true);
        catVideo.getElement().setAttribute("playsinline", true);
        catVideo.addClassName("hero-video-frame");

        heroGraphic.add(catVideo);

        Button petCatBtn = new Button("PET CAT");
        petCatBtn.addClassName("arcade-btn");
        petCatBtn.addClassName("pink");
        petCatBtn.getStyle().set("width", "100%");
        petCatBtn.getStyle().set("max-width", "200px");
        petCatBtn.addClickListener(e -> {
            UI.getCurrent().getPage().executeJs("window.spawnHearts('hero-cat-graphic');");
        });

        heroGraphicWrapper.add(heroGraphic, petCatBtn);
        heroWrapper.add(heroGraphicWrapper);

        introSection.add(heroWrapper);

        // Bouncing scroll indicator
        Anchor scrollArrow = new Anchor("#projects", "▼");
        scrollArrow.addClassName("scroll-down-indicator");
        introSection.add(scrollArrow);
        
        add(introSection);

        // ==========================================
        // SECTION 2: PROJECTS (#projects)
        // ==========================================
        Div projectsSection = new Div();
        projectsSection.setId("projects");
        projectsSection.addClassName("scroll-section");

        H2 projTitle = new H2("ENGINEERING SHOWCASE");
        projTitle.addClassName("retro-title");
        projectsSection.add(projTitle);

        Div projectsContainer = new Div();
        projectsContainer.getStyle().set("width", "100%");
        projectsContainer.getStyle().set("max-width", "100%");
        projectsContainer.getStyle().set("padding", "0 6rem");
        projectsContainer.getStyle().set("box-sizing", "border-box");
        projectsContainer.getStyle().set("margin", "0 auto");

        // Row 1: NES Emulator
        projectsContainer.add(createProjectRow(
            "NES EMULATOR",
            "EMULATION/GAMES",
            "A cycle-accurate console emulator emulating CPU 6502 instructions, register memory pipelines, and APU pulse waves. Designed to explore physical hardware constraints inside Java JVM architectures.",
            "themes/retrotheme/nes_gameplay.mp4",
            "JAVA, ASSEMBLY, HARDWARE EMULATION",
            false,
            "https://github.com/mrborn2suffer/NES-or-Mess"
        ));

        // Row 2: Wolf3D Raycaster (Alt layout)
        projectsContainer.add(createProjectRow(
            "WOLF3D ENGINE",
            "SOFTWARE RENDERING",
            "A pseudo-3D raycaster software renderer texture-mapping corridors in real-time. Explores math vector projections, software optimization, threads, and physics in software rendering.",
            "themes/retrotheme/wolf3d_demo.mp4",
            "JAVA, DDA MATH, GAME ENGINE, PHYSICS",
            true,
            "https://github.com/mrborn2suffer/Wolfstein"
        ));

        // Row 3: Aurora Library
        projectsContainer.add(createProjectRow(
            "AURORA LIBRARY",
            "LIBRARY MANAGEMENT",
            "A fullstack Library Management System allowing users to search books by genre and administrators to manage members and inventory. Implements REST APIs with Spring Boot and React.",
            "themes/retrotheme/library_demo.mp4",
            "SPRING BOOT, MONGODB, REACT, VITE, REST API",
            false,
            "https://github.com/mrborn2suffer/library-fullstack"
        ));

        // Row 4: PingSentinel (Alt layout)
        projectsContainer.add(createProjectRow(
            "PING SENTINEL",
            "MONITORING SYSTEM",
            "A deep-crawling website and API monitoring engine that extracts site sublinks using BFS, validates endpoints asynchronously, rolls up metrics atomically in MongoDB, and triggers alert state machines.",
            "themes/retrotheme/ping_sentinel_demo.mp4",
            "SPRING BOOT, MONGODB, SELENIUM, ASYNC VALIDATION, QUARTZ",
            true,
            "https://github.com/mrborn2suffer/PingSentinal"
        ));

        projectsSection.add(projectsContainer);
        add(projectsSection);

        // ==========================================
        // SECTION 3: BIOGRAPHY & ABILITY METERS (#about)
        // ==========================================
        Div aboutSection = new Div();
        aboutSection.setId("about");
        aboutSection.addClassName("scroll-section");

        H2 aboutTitle = new H2("BIOGRAPHY & INVENTORY");
        aboutTitle.addClassName("retro-title");
        aboutSection.add(aboutTitle);

        Div aboutGrid = new Div();
        aboutGrid.addClassName("layout-grid-2");
        aboutGrid.getStyle().set("width", "100%");
        aboutGrid.getStyle().set("max-width", "1100px");
        aboutGrid.getStyle().set("margin", "0 auto");

        // Column 1: Bio & Inventory Slots
        Div colLeft = new Div();
        colLeft.getStyle().set("display", "flex");
        colLeft.getStyle().set("flex-direction", "column");
        colLeft.getStyle().set("gap", "2rem");

        Div bioCard = new Div();
        bioCard.addClassName("retro-card");

        H3 bioHeader = new H3("SYSTEM OPERATOR SPECS");
        bioHeader.addClassName("retro-title");
        bioHeader.getStyle().set("margin-bottom", "1.5rem");

        Div bioDetails = new Div();
        bioDetails.getStyle().set("display", "flex");
        bioDetails.getStyle().set("flex-direction", "column");
        bioDetails.getStyle().set("gap", "0.6rem");
        bioDetails.getStyle().set("font-family", "var(--font-mono)");
        bioDetails.getStyle().set("font-size", "1.1rem");
        bioDetails.getStyle().set("margin-bottom", "1.5rem");

        bioDetails.add(createBioLine("OPERATOR NAME:", "VAIBHAV"));
        bioDetails.add(createBioLine("CLASSIFICATION:", "BACKEND SOFTWARE ENGINEER"));
        bioDetails.add(createBioLine("PRIMARY GUILD:", "JAVA & BACKEND SYSTEMS"));
        bioDetails.add(createBioLine("EXPERIENCE:", "5+ YEARS ACTIVE"));

        Paragraph bioDesc = new Paragraph(
            "My passion for software started with a love for retro tech—specifically, figuring out how old-school computers turned raw code into moving pixels on a screen. " +
            "This curiosity drove me to dive deep into how computers work at the lowest level, leading me to build complex things completely from scratch, like vintage game emulators and classic 3D graphics engines.\n\n" +
            "Today, I bring that same obsession with speed and efficiency into modern web development. " +
            "As a backend engineer, I build powerful, reliable systems that can handle thousands of users smoothly, manage data instantly, and deliver clean, interactive dashboards for users."
        );
        bioDesc.addClassName("retro-text");
        
        bioCard.add(bioHeader, bioDetails, bioDesc);
        colLeft.add(bioCard);

        // Workstation Slots
        Div inventoryCard = new Div();
        inventoryCard.addClassName("retro-card");

        H3 invHeader = new H3("INVENTORY EQUIPMENT");
        invHeader.addClassName("retro-title");
        invHeader.getStyle().set("margin-bottom", "1.5rem");
        inventoryCard.add(invHeader);

        Div invGrid = new Div();
        invGrid.getStyle().set("display", "grid");
        invGrid.getStyle().set("grid-template-columns", "1fr 1fr");
        invGrid.getStyle().set("gap", "1rem");

        invGrid.add(createInventorySlot("BRAIN", "Neural network (+50 Logic).", VaadinIcon.LIGHTBULB));
        invGrid.add(createInventorySlot("JAVA & SPRING BOOT", "Primary fuel (boosts development speed).", VaadinIcon.CODE));
        invGrid.add(createInventorySlot("MAVEN", "Structure build protection shield.", VaadinIcon.SHIELD));
        invGrid.add(createInventorySlot("GIT", "Version control resolve sword.", VaadinIcon.ROAD_BRANCH));

        inventoryCard.add(invGrid);
        colLeft.add(inventoryCard);
        aboutGrid.add(colLeft);

        // Column 2: Skill Meters
        Div colRight = new Div();
        Div statsCard = new Div();
        statsCard.addClassName("retro-card");

        H3 statsHeader = new H3("TECHNICAL ABILITY METERS");
        statsHeader.addClassName("retro-title");
        statsHeader.getStyle().set("margin-bottom", "1.5rem");
        statsCard.add(statsHeader);

        statsCard.add(createStatBar("JAVA (SDK 8 - 21)", 95));
        statsCard.add(createStatBar("SPRING BOOT & MICROSERVICES", 90));
        statsCard.add(createStatBar("VAADIN UI / WEBSOCKETS", 85));
        statsCard.add(createStatBar("SQL / POSTGRESQL / REDIS CACHE", 85));
        statsCard.add(createStatBar("VANILLA JAVASCRIPT / HTML5 / CSS3", 80));
        statsCard.add(createStatBar("DOCKER / CI-CD ACTIONS / BASH SH", 75));

        colRight.add(statsCard);
        aboutGrid.add(colRight);

        aboutSection.add(aboutGrid);
        add(aboutSection);

        // ==========================================
        // SECTION 4: API PLAYGROUND CONSOLE (#api-console)
        // ==========================================
        Div boardSection = new Div();
        boardSection.setId("api-console");
        boardSection.addClassName("scroll-section");

        H2 boardTitle = new H2("MAINFR_API INTERACTION PORT");
        boardTitle.addClassName("retro-title");
        boardSection.add(boardTitle);

        Div boardGrid = new Div();
        boardGrid.getStyle().set("display", "flex");
        boardGrid.getStyle().set("flex-direction", "column");
        boardGrid.getStyle().set("gap", "3rem");
        boardGrid.getStyle().set("width", "100%");
        boardGrid.getStyle().set("max-width", "1400px");
        boardGrid.getStyle().set("margin", "0 auto");
        boardGrid.getStyle().set("align-items", "center");

        // Console card (wide card)
        Div consoleCard = new Div();
        consoleCard.addClassName("retro-card");
        consoleCard.getStyle().set("display", "flex");
        consoleCard.getStyle().set("flex-direction", "column");
        consoleCard.getStyle().set("align-items", "center");
        consoleCard.getStyle().set("width", "100%");
        consoleCard.getStyle().set("max-width", "1300px");

        H3 playHeader = new H3("SYSTEM GATEWAY CLIENT");
        playHeader.addClassName("retro-title");
        playHeader.getStyle().set("margin-bottom", "1.2rem");
        consoleCard.add(playHeader);

        com.portfolio.components.ApiConsole console = new com.portfolio.components.ApiConsole();
        consoleCard.add(console);
        boardGrid.add(consoleCard);

        boardSection.add(boardGrid);
        add(boardSection);

        // ==========================================
        // ==========================================
        // SECTION 5: SOCIAL CHANNELS & CONNECTIONS (#contact)
        // ==========================================
        Div contactSection = new Div();
        contactSection.setId("contact");
        contactSection.addClassName("scroll-section");

        Div contactWrapper = new Div();
        contactWrapper.getStyle().set("max-width", "1500px");
        contactWrapper.getStyle().set("margin", "0 auto");
        contactWrapper.getStyle().set("width", "100%");

        H2 contactTitle = new H2("COMMUNICATION CHANNELS");
        contactTitle.addClassName("retro-title");
        contactTitle.getStyle().set("text-align", "center");
        contactTitle.getStyle().set("display", "block");
        contactTitle.getStyle().set("margin-bottom", "3rem");
        contactWrapper.add(contactTitle);

        Div socialGrid = new Div();
        socialGrid.addClassName("social-grid");

        socialGrid.add(createSocialCard(
            "GITHUB",
            "SOURCE CODE REPOSITORY",
            "Browse emulators, rendering engines, and system schemas.",
            "https://github.com/mrborn2suffer",
            "M12 2A10 10 0 0 0 2 12c0 4.42 2.87 8.17 6.84 9.5.5.08.66-.23.66-.5v-1.69c-2.77.6-3.36-1.34-3.36-1.34-.46-1.16-1.11-1.47-1.11-1.47-.9-.62.07-.6.07-.6 1 .07 1.53 1.03 1.53 1.03.9 1.52 2.34 1.07 2.91.83.09-.65.35-1.09.63-1.34-2.22-.25-4.55-1.11-4.55-4.92 0-1.11.38-2 1.03-2.71-.1-.25-.45-1.29.1-2.64 0 0 .84-.27 2.75 1.02.79-.22 1.65-.33 2.5-.33.85 0 1.71.11 2.5.33 1.91-1.29 2.75-1.02 2.75-1.02.55 1.35.2 2.39.1 2.64.65.71 1.03 1.6 1.03 2.71 0 3.82-2.34 4.66-4.57 4.91.36.31.69.92.69 1.85V21c0 .27.16.59.67.5C19.14 20.16 22 16.42 22 12A10 10 0 0 0 12 2z",
            "shulker-github"
        ));

        socialGrid.add(createSocialCard(
            "LINKEDIN",
            "PROFESSIONAL NETWORK",
            "Inspect corporate credentials and industry endorsements.",
            "https://www.linkedin.com/in/vaii2006",
            "M19 3a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h14m-.5 15.5v-5.3a3.26 3.26 0 0 0-3.26-3.26c-.85 0-1.84.52-2.32 1.3v-1.11h-2.79v8.37h2.79v-4.93c0-.77.62-1.4 1.39-1.4a1.4 1.4 0 0 1 1.4 1.4v4.93h2.79M6.88 8.56a1.68 1.68 0 0 0 1.68-1.68c0-.93-.75-1.69-1.68-1.69a1.69 1.69 0 0 0-1.69 1.69c0 .93.76 1.68 1.69 1.68m1.39 9.94v-8.37H5.5v8.37h2.77z",
            "shulker-linkedin"
        ));

        socialGrid.add(createSocialCard(
            "DISCORD",
            "COMMUNITY SERVER",
            "Join the dev channels and chat low-level emulators.",
            "https://discord.com/users/831545210973454337",
            "M19.27 4.73a16.13 16.13 0 0 0-3.97-1.23.15.15 0 0 0-.16.07c-.17.31-.36.72-.5 1.04a15 15 0 0 0-5.28 0c-.14-.32-.33-.73-.51-1.04a.15.15 0 0 0-.16-.07 16.12 16.12 0 0 0-3.97 1.23.13.13 0 0 0-.06.05A16.17 16.17 0 0 0 1.8 17c0 .02 0 .04.02.05a16.16 16.16 0 0 0 4.88 2.47.14.14 0 0 0 .15-.05c.42-.58.8-1.2 1.13-1.85a.14.14 0 0 0-.08-.2 10.7 10.7 0 0 1-1.53-.73.14.14 0 0 1-.01-.24c.1-.07.2-.15.3-.23a.13.13 0 0 1 .14-.02 11.24 11.24 0 0 0 10.84 0 .13.13 0 0 1 .14.02c.1.08.2.16.3.23a.14.14 0 0 1-.01.24 9.6 9.6 0 0 1-1.53.73.14.14 0 0 0-.08.2c.33.65.71 1.27 1.13 1.85a.14.14 0 0 0 .15.05 16.14 16.14 0 0 0 4.9-2.47.14.14 0 0 0 .02-.05 16.13 16.13 0 0 0-3.05-12.22.12.12 0 0 0-.06-.05zM8.02 15.33c-.93 0-1.7-1.12-1.7-1.82 0-.7.76-1.82 1.7-1.82.93 0 1.7 1.12 1.7 1.82 0 .7-.76 1.82-1.7 1.82zm7.96 0c-.93 0-1.7-1.12-1.7-1.82 0-.7.76-1.82 1.7-1.82.93 0 1.7 1.12 1.7 1.82 0 .7-.76 1.82-1.7 1.82z",
            "shulker-discord"
        ));

        socialGrid.add(createSocialCard(
            "EMAIL",
            "DIRECT COORDINATES",
            "Send secure message packet to mr.born2suffer@gmail.com.",
            "mailto:mr.born2suffer@gmail.com",
            "M20 4H4c-1.1 0-1.99.9-1.99 2L2 18c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm0 4l-8 5-8-5V6l8 5 8-5v2z",
            "shulker-email"
        ));

        contactWrapper.add(socialGrid);

        // Thank You Section
        Div thankYouSection = new Div();
        thankYouSection.addClassName("thank-you-section");

        H2 thankYouTitle = new H2("THANK YOU");
        thankYouTitle.addClassName("thank-you-title");

        thankYouSection.add(thankYouTitle);
        contactWrapper.add(thankYouSection);

        contactSection.add(contactWrapper);
        add(contactSection);
    }

    private void scrollToSection(String sectionId) {
        String scrollJS = "const sec = document.getElementById('" + sectionId + "');\n" +
                "if (sec) sec.scrollIntoView({ behavior: 'smooth' });";
        UI.getCurrent().getPage().executeJs(scrollJS);
    }

    private boolean isSideFace(String name) {
        return "front".equals(name) || "back".equals(name) || "left".equals(name) || "right".equals(name);
    }

    private String getSideFaceSvgHtml(boolean isLid) {
        String outerD = isLid 
            ? "M12 12 H268 V128 H233 V88 H47 V128 H12 Z"
            : "M12 168 V52 H47 V12 H233 V52 H268 V168 Z";
            
        String innerD = isLid
            ? "M36 36 H244 V104 H221 V64 H59 V104 H36 Z"
            : "M36 144 V76 H59 V36 H221 V76 H244 V144 Z";
            
        return String.format(
            "<svg viewBox='0 0 280 %d' width='100%%' height='100%%' class='shulker-svg' xmlns='http://www.w3.org/2000/svg'>" +
            "  <path d='%s' class='outer-shell' stroke-width='24' stroke-linejoin='miter' />" +
            "  <path d='%s' class='inner-pocket' stroke-width='16' stroke-linejoin='miter' />" +
            "</svg>",
            isLid ? 140 : 180, outerD, innerD
        );
    }

    private String getCapFaceSvgHtml() {
        String outerD = "M12 12 H268 V268 H12 Z";
        String innerD = "M36 36 H244 V244 H36 Z";
        
        return String.format(
            "<svg viewBox='0 0 280 280' width='100%%' height='100%%' class='shulker-svg' xmlns='http://www.w3.org/2000/svg'>" +
            "  <path d='%s' class='outer-shell' stroke-width='24' stroke-linejoin='miter' />" +
            "  <path d='%s' class='inner-pocket' stroke-width='16' stroke-linejoin='miter' />" +
            "</svg>",
            outerD, innerD
        );
    }

    private Div createSocialCard(String title, String subtitle, String desc, String url, String svgPath, String colorClass) {
        Div wrapper = new Div();
        wrapper.addClassName("shulker-box-wrapper");
        wrapper.addClassName(colorClass);

        Div box3d = new Div();
        box3d.addClassName("shulker-box-3d");

        // LID (spins & goes up on hover)
        Div lid = new Div();
        lid.addClassName("shulker-lid");
        String[] lidFaces = {"front", "back", "left", "right", "top"};
        for (String faceName : lidFaces) {
            Div face = new Div();
            face.addClassName("face");
            face.addClassName(faceName);
            if (isSideFace(faceName)) {
                face.getElement().setProperty("innerHTML", getSideFaceSvgHtml(true));
            } else {
                face.getElement().setProperty("innerHTML", getCapFaceSvgHtml());
            }
            lid.add(face);
        }

        // BASE (goes down slightly on hover)
        Div base = new Div();
        base.addClassName("shulker-base");
        String[] baseFaces = {"front", "back", "left", "right", "bottom"};
        for (String faceName : baseFaces) {
            Div face = new Div();
            face.addClassName("face");
            face.addClassName(faceName);
            if (isSideFace(faceName)) {
                face.getElement().setProperty("innerHTML", getSideFaceSvgHtml(false));
            } else {
                face.getElement().setProperty("innerHTML", getCapFaceSvgHtml());
            }
            base.add(face);
        }

        // PAYLOAD (the actual interactive card inside the shulker box)
        com.vaadin.flow.component.html.Anchor card = new com.vaadin.flow.component.html.Anchor(url, "");
        card.setTarget("_blank");
        card.addClassName("shulker-payload");

        com.vaadin.flow.component.HtmlContainer svg = new com.vaadin.flow.component.HtmlContainer("svg");
        svg.getElement().setAttribute("viewBox", "0 0 24 24");
        svg.getElement().setAttribute("fill", "currentColor");
        svg.addClassName("social-icon");
        
        com.vaadin.flow.component.HtmlComponent path = new com.vaadin.flow.component.HtmlComponent("path");
        path.getElement().setAttribute("d", svgPath);
        svg.add(path);

        Div content = new Div();
        content.addClassName("social-content");

        H3 cardTitle = new H3(title);
        cardTitle.addClassName("retro-title");
        cardTitle.getStyle().set("margin-bottom", "4px");
        cardTitle.getStyle().set("font-size", "1.2rem");

        Span cardSub = new Span(subtitle);
        cardSub.getStyle().set("font-family", "var(--font-mono)");
        cardSub.getStyle().set("font-size", "0.8rem");
        cardSub.getStyle().set("color", "var(--accent-purple)");
        cardSub.getStyle().set("text-transform", "uppercase");
        cardSub.getStyle().set("margin-bottom", "8px");
        cardSub.getStyle().set("display", "block");

        Paragraph cardDesc = new Paragraph(desc);
        cardDesc.addClassName("retro-text");
        cardDesc.getStyle().set("font-size", "0.9rem");
        cardDesc.getStyle().set("margin", "0");
        cardDesc.getStyle().set("line-height", "1.4");

        content.add(cardTitle, cardSub, cardDesc);
        card.add(svg, content);

        box3d.add(lid, base, card);
        wrapper.add(box3d);
        return wrapper;
    }


    private Div createProjectRow(String title, String category, String desc, String videoUrl, String tags, boolean alt, String githubUrl) {
        Div row = new Div();
        row.addClassName("project-row");
        if (alt) {
            row.addClassName("alt");
        }

        // Left: Video side
        Div videoSide = new Div();
        videoSide.addClassName("video-side");

        if (videoUrl.endsWith(".mp4")) {
            com.vaadin.flow.component.HtmlContainer videoElement = new com.vaadin.flow.component.HtmlContainer("video");
            videoElement.getElement().setAttribute("src", videoUrl);
            videoElement.getElement().setAttribute("autoplay", true);
            videoElement.getElement().setAttribute("loop", true);
            videoElement.getElement().setAttribute("muted", true);
            videoElement.getElement().setAttribute("playsinline", true);
            videoElement.addClassName("project-video-frame");
            videoElement.addClassName("hover-audio-video");
            videoSide.add(videoElement);
        } else if (videoUrl.endsWith(".jpg") || videoUrl.endsWith(".jpeg") || videoUrl.endsWith(".png") || videoUrl.endsWith(".gif")) {
            Image imgElement = new Image(videoUrl, title);
            imgElement.addClassName("project-video-frame");
            videoSide.add(imgElement);
        } else {
            IFrame video = new IFrame(videoUrl);
            video.addClassName("project-video-frame");
            videoSide.add(video);
        }
        row.add(videoSide);

        // Right: Details side
        Div detailsSide = new Div();
        detailsSide.addClassName("details-side");

        Anchor titleLink = new Anchor(githubUrl, title);
        titleLink.setTarget("_blank");
        titleLink.addClassName("project-title-link");

        H3 rowTitle = new H3(titleLink);
        rowTitle.addClassName("retro-title");

        Paragraph rowDesc = new Paragraph(desc);
        rowDesc.addClassName("retro-text");

        Div tagsContainer = new Div();
        tagsContainer.getStyle().set("display", "flex");
        tagsContainer.getStyle().set("gap", "10px");
        tagsContainer.getStyle().set("flex-wrap", "wrap");

        for (String tag : tags.split(",\\s*")) {
            Span tagSpan = new Span(tag);
            tagSpan.addClassName("retro-tag");
            tagsContainer.add(tagSpan);
        }

        detailsSide.add(rowTitle, rowDesc, tagsContainer);
        row.add(detailsSide);

        return row;
    }

    private Div createBioLine(String label, String value) {
        Div line = new Div();
        line.getStyle().set("display", "flex");
        line.getStyle().set("justify-content", "space-between");
        line.getStyle().set("border-bottom", "1px dashed #333");
        line.getStyle().set("padding-bottom", "4px");
        
        Span labelSpan = new Span(label);
        labelSpan.getStyle().set("color", "#888");
        
        Span valueSpan = new Span(value);
        valueSpan.getStyle().set("color", "#fff");
        
        line.add(labelSpan, valueSpan);
        return line;
    }

    private Div createInventorySlot(String title, String desc, VaadinIcon iconType) {
        Div slot = new Div();
        slot.getStyle().set("background-color", "#08080f");
        slot.getStyle().set("border", "1px solid var(--border-color)");
        slot.getStyle().set("padding", "0.8rem");
        slot.getStyle().set("display", "flex");
        slot.getStyle().set("gap", "0.8rem");
        
        Icon icon = new Icon(iconType);
        icon.getStyle().set("color", "var(--accent-purple)");
        icon.getStyle().set("font-size", "1.2rem");
        slot.add(icon);

        Div textWrap = new Div();
        textWrap.getStyle().set("display", "flex");
        textWrap.getStyle().set("flex-direction", "column");
        textWrap.getStyle().set("gap", "2px");

        Span titleSpan = new Span(title);
        titleSpan.getStyle().set("font-family", "var(--font-title)");
        titleSpan.getStyle().set("font-size", "0.9rem");
        titleSpan.getStyle().set("color", "#ffffff");

        Span descSpan = new Span(desc);
        descSpan.getStyle().set("font-family", "var(--font-sans)");
        descSpan.getStyle().set("font-size", "0.85rem");
        descSpan.getStyle().set("color", "#888");

        textWrap.add(titleSpan, descSpan);
        slot.add(textWrap);

        return slot;
    }

    private Div createStatBar(String skillName, int percent) {
        Div container = new Div();
        container.addClassName("stat-container");

        Div label = new Div();
        label.addClassName("stat-label");
        Span nameSpan = new Span(skillName);
        Span percentSpan = new Span(percent + "% XP");
        label.add(nameSpan, percentSpan);

        Div barOuter = new Div();
        barOuter.addClassName("stat-bar-outer");

        Div barInner = new Div();
        barInner.addClassName("stat-bar-inner");
        barInner.addClassName("pink");
        barInner.getElement().setAttribute("data-width", String.valueOf(percent));
        
        barOuter.add(barInner);
        container.add(label, barOuter);
        return container;
    }

    private void triggerBarAnimation() {
        String script = "setTimeout(() => {\n" +
                "    document.querySelectorAll('.stat-bar-inner').forEach(bar => {\n" +
                "        const width = bar.getAttribute('data-width');\n" +
                "        bar.style.width = width + '%';\n" +
                "    });\n" +
                "}, 150);";
        UI.getCurrent().getPage().executeJs(script);
    }

    private void setupShowcaseVideos() {
        String script = """
                setTimeout(() => {
                    const videos = document.querySelectorAll('.hover-audio-video');
                    videos.forEach(video => {
                        video.loop = true;
                        video.muted = true;
                        video.setAttribute('muted', 'true');
                        video.setAttribute('playsinline', 'true');
                        
                        // Play fallback on user interaction to bypass browser autoplay blocks
                        const playVideo = () => {
                            if (video.paused) {
                                video.play().catch(err => {});
                            }
                        };
                        document.addEventListener('click', playVideo, { once: true });
                        document.addEventListener('touchstart', playVideo, { once: true });
                        document.addEventListener('keydown', playVideo, { once: true });
                        
                        // Play immediately
                        video.play().catch(err => {
                            console.log("Autoplay waiting for interaction: ", err);
                        });

                        // Unmute on hover
                        video.addEventListener('mouseenter', () => {
                            video.muted = false;
                        });
                        
                        // Mute on hover leave
                        video.addEventListener('mouseleave', () => {
                            video.muted = true;
                        });

                        // Keep playing as long as any part is visible
                        try {
                            const observer = new IntersectionObserver((entries) => {
                                entries.forEach(entry => {
                                    if (entry.isIntersecting) {
                                        video.play().catch(err => {});
                                    } else {
                                        video.pause();
                                    }
                                });
                            }, {
                                root: null,
                                threshold: 0.01
                            });
                            observer.observe(video);
                        } catch (e) {
                            console.error("IntersectionObserver failed: ", e);
                        }
                    });
                }, 200);
                """;
        UI.getCurrent().getPage().executeJs(script);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        triggerBarAnimation();
        setupShowcaseVideos();
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
    }
}

