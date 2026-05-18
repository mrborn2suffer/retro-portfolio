package com.portfolio.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "terminal", layout = MainLayout.class)
@PageTitle("Terminal | Vaibhav's Developer Console")
public class TerminalView extends Div {

    private final Div history = new Div();
    private final TextField input = new TextField();

    public TerminalView() {
        addClassName("terminal-view");

        // Grid background
        Div gridBg = new Div();
        gridBg.addClassName("grid-bg");
        add(gridBg);

        // Header section
        Div headerContainer = new Div();
        headerContainer.getStyle().set("text-align", "center");
        headerContainer.getStyle().set("margin-bottom", "2rem");

        H1 title = new H1("DEVELOPER CONSOLE TTY1");
        title.addClassName("retro-title");
        title.getStyle().set("font-size", "2.5rem");
        title.getStyle().set("margin-bottom", "0.5rem");

        Paragraph subtitle = new Paragraph("A high-contrast command shell directly interfacing with player Vaibhav's credentials.");
        subtitle.addClassName("retro-text");
        subtitle.getStyle().set("font-size", "1.1rem");

        headerContainer.add(title, subtitle);
        add(headerContainer);

        // Terminal Console Card
        Div terminalCard = new Div();
        terminalCard.addClassName("terminal-view-card");

        history.addClassName("terminal-history");
        terminalCard.add(history);

        // Input Line
        Div inputLine = new Div();
        inputLine.addClassName("terminal-input-line");

        Span promptPrefix = new Span("guest@vaibhav:~$");
        promptPrefix.addClassName("terminal-prompt-prefix");

        input.addClassName("terminal-input-field");
        input.setPlaceholder("TYPE 'help' FOR LIST OF SYSTEM COMMANDS...");
        input.setAutofocus(true);
        input.setClearButtonVisible(false);

        // Listener for ENTER key
        input.addKeyDownListener(Key.ENTER, event -> {
            String value = input.getValue();
            if (value != null) {
                processCommand(value);
            }
            input.clear();
        });

        inputLine.add(promptPrefix, input);
        terminalCard.add(inputLine);
        add(terminalCard);

        // Print initial boot messages
        showBootMessage();
    }

    private void showBootMessage() {
        appendLine("VAIBHAV_ARCADE MAINFRAME v1.0.0 (TTY1)", "header-line");
        appendLine("BOOT STATUS: 100% COMPILED. ALL INTERFACES ONLINE.", "success-line");
        appendLine("CHIPTUNE AUDIO SUBSYSTEM: READY.", "success-line");
        appendLine("TYPE 'help' FOR A LIST OF AVAILABLE SYSTEM COMMANDS.", "prompt-line");
        appendLine(" ", "");
    }

    private void appendLine(String text, String className) {
        Div line = new Div(new Span(text));
        if (className != null && !className.isEmpty()) {
            line.addClassName(className);
        }
        history.add(line);
    }

    private void processCommand(String rawCommand) {
        if (rawCommand == null) return;
        String trimmed = rawCommand.trim();
        appendLine("guest@vaibhav:~$ " + trimmed, "prompt-line");

        if (trimmed.isEmpty()) {
            scrollToBottom();
            return;
        }

        String command = trimmed.toLowerCase();
        if (command.equals("/contact")) {
            appendLine("NAVIGATING TO CONTACT TRANSMISSION TERMINAL...", "success-line");
            getUI().ifPresent(ui -> ui.navigate("contact"));
            scrollToBottom();
            return;
        }
        if (command.startsWith("/")) {
            command = command.substring(1);
        }
        String[] parts = command.split("\\s+");
        String baseCmd = parts[0];

        switch (baseCmd) {
            case "help" -> showHelp();
            case "about" -> showAbout();
            case "skills" -> showSkills();
            case "projects" -> showProjects();
            case "resume" -> showResume();
            case "clear" -> clearScreen();
            case "game" -> {
                appendLine("ARCADE CABINET OFFLINE: STEEL RACER HAS BEEN DECOMMISSIONED.", "error-line");
            }
            case "contact" -> showContact();
            case "inject-bug" -> triggerGlitchEffect();
            default -> appendLine("ERROR: COMMAND '" + baseCmd + "' NOT RECOGNIZED. TYPE 'help' FOR DIAGNOSTICS.", "error-line");
        }
        scrollToBottom();
    }
    private void showHelp() {
        appendLine("==================================================", "");
        appendLine("AVAILABLE SYSTEM COMMANDS:", "header-line");
        appendLine("  help        Display this diagnostic menu", "");
        appendLine("  about       Print player bio & background profile", "");
        appendLine("  skills      Load hardware engineering skill meters", "");
        appendLine("  projects    List engineering projects & repos", "");
        appendLine("  resume      Download and view professional resume (PDF)", "");
        appendLine("  contact     Print communication coordinates", "");
        appendLine("  inject-bug  Run simulation debug error sequence", "");
        appendLine("  clear       Wipe console screen history", "");
        appendLine("==================================================", "");
    }

    private void showResume() {
        appendLine("--------------------------------------------------", "");
        appendLine("RESUME PACKET INFORMATION:", "header-line");
        appendLine("  DOCUMENT: resume.pdf", "");
        appendLine("  FORMAT: PDF (Application/PDF)", "");
        appendLine("  DESCRIPTION: Operator Vaibhav's Professional Resume Packet.", "");
        appendLine("  STATUS: Dispatching view/download signal to browser...", "success-line");
        
        Div linkContainer = new Div();
        linkContainer.addClassName("prompt-line");
        Span prefix = new Span("  DIRECT LINK: ");
        Anchor downloadLink = new Anchor("https://drive.google.com/file/d/1B2fFAJUsMzyJf_mu7X1u3K2CeN0COkqv/view?usp=sharing", "CLICK HERE TO DOWNLOAD/VIEW");
        downloadLink.setTarget("_blank");
        downloadLink.getStyle().set("color", "#00ff00");
        downloadLink.getStyle().set("text-decoration", "underline");
        linkContainer.add(prefix, downloadLink);
        history.add(linkContainer);
        
        appendLine("--------------------------------------------------", "");

        UI.getCurrent().getPage().executeJs("window.open('https://drive.google.com/file/d/1B2fFAJUsMzyJf_mu7X1u3K2CeN0COkqv/view?usp=sharing', '_blank');");
    }

    private void showAbout() {
        appendLine("--------------------------------------------------", "");
        appendLine("PROFILE SPECIFICATIONS:", "header-line");
        appendLine("  NAME: Vaibhav", "");
        appendLine("  ROLE: Fullstack Software Engineer", "");
        appendLine("  SPECIALIZATION: Java & Concurrent Backend Systems", "");
        appendLine("  PHILOSOPHY: Efficiency, first principles, cycle-accurate logic.", "");
        appendLine("  BIO: Fascinated by retro constraints, Vaibhav writes emulators", "");
        appendLine("       and raycasters in pure Java, combined with robust, high-", "");
        appendLine("       concurrency backend APIs utilizing Spring Boot and Redis.", "");
        appendLine("--------------------------------------------------", "");
    }

    private void showSkills() {
        appendLine("--------------------------------------------------", "");
        appendLine("TECHNICAL COMPETENCIES (XP LEVEL):", "header-line");
        appendLine("  JAVA (SDK 8-21)               [=================== ] 95%", "");
        appendLine("  SPRING BOOT & MICROSERVICES   [==================  ] 90%", "");
        appendLine("  VAADIN UI / WEBSOCKETS        [=================   ] 85%", "");
        appendLine("  SQL & REDIS MEMORY CACHE      [=================   ] 85%", "");
        appendLine("  JAVASCRIPT / HTML5 / CSS3     [================    ] 80%", "");
        appendLine("  DOCKER / DEVOPS / BASH SH     [===============     ] 75%", "");
        appendLine("--------------------------------------------------", "");
    }

    private void showProjects() {
        appendLine("--------------------------------------------------", "");
        appendLine("FLAGSHIP PROJECT DIRECTORY:", "header-line");
        appendLine("  1. NES EMULATOR", "");
        appendLine("     Category: Emulation / Systems", "");
        appendLine("     Stack: Java, 6502 Assembly, PPU buffer", "");
        appendLine("     Desc: Cycle-accurate NES console emulator written from scratch.", "");
        appendLine("  2. WOLF3D ENGINE", "");
        appendLine("     Category: Software Rendering", "");
        appendLine("     Stack: Java, DDA Math, Multi-threading", "");
        appendLine("     Desc: Raycasting 3D textured corridors in real-time.", "");
        appendLine("  3. AURORA LIBRARY", "");
        appendLine("     Category: Library Management", "");
        appendLine("     Stack: Spring Boot, MongoDB, React, Vite", "");
        appendLine("     Desc: Fullstack Library System with member and inventory controls.", "");
        appendLine("  4. PINGSENTINEL", "");
        appendLine("     Category: Monitoring Systems", "");
        appendLine("     Stack: Spring Boot, MongoDB, Selenium, Quartz", "");
        appendLine("     Desc: Deep-crawling website and API monitoring engine.", "");
        appendLine("--------------------------------------------------", "");
    }

    private void showContact() {
        appendLine("--------------------------------------------------", "");
        appendLine("COMMUNICATION CHANNELS:", "header-line");
        appendLine("  EMAIL: mr.born2suffer@gmail.com", "");
        appendLine("  GITHUB: github.com/mrborn2suffer", "");
        appendLine("  LINKEDIN: linkedin.com/in/vaii2006", "");
        appendLine("  DISCORD: discord.com/users/831545210973454337", "");
        appendLine("  TO TRANSMIT A MEMO, VISIT THE '/contact' PAGE.", "success-line");
        appendLine("--------------------------------------------------", "");
    }

    private void clearScreen() {
        history.removeAll();
        appendLine("TIP : TYPE 'help' FOR A LIST OF AVAILABLE SYSTEM COMMANDS.", "prompt-line");
    }

    private void triggerGlitchEffect() {
        appendLine("!!! SYSTEM INTRUSION SIMULATION DETECTED !!!", "error-line");
        appendLine("INJECTING FAKE BUGS INTO THREAD POOLS...", "error-line");
        
        String glitchScript = """
            const historyDiv = document.querySelector('.terminal-history');
            const words = ['[ERR]', '[FATAL]', '[CRASH]', '[OVERFLOW]', '[GLITCH]', '[INTRUDER]'];
            let count = 0;
            const interval = setInterval(() => {
                const line = document.createElement('div');
                line.style.color = '#c62828';
                line.style.fontFamily = 'monospace';
                line.style.fontWeight = 'bold';
                line.textContent = 'STACK DUMP: ' + words[Math.floor(Math.random() * words.length)] + ' at 0x' + Math.floor(Math.random()*16777215).toString(16).toUpperCase();
                historyDiv.appendChild(line);
                historyDiv.scrollTop = historyDiv.scrollHeight;
                count++;
                if (count > 15) {
                    clearInterval(interval);
                    const finalLine = document.createElement('div');
                    finalLine.style.color = '#2e7d32';
                    finalLine.style.fontWeight = 'bold';
                    finalLine.textContent = 'SYSTEM CONSOLE STABILIZED. BUG REMEDIAL COMPLETED.';
                    historyDiv.appendChild(finalLine);
                    historyDiv.scrollTop = historyDiv.scrollHeight;
                }
            }, 80);
            """;
        
        UI.getCurrent().getPage().executeJs(glitchScript);
    }

    private void scrollToBottom() {
        String scrollScript = "setTimeout(() => {\n" +
                "    const historyDiv = document.querySelector('.terminal-history');\n" +
                "    if (historyDiv) {\n" +
                "        historyDiv.scrollTop = historyDiv.scrollHeight;\n" +
                "    }\n" +
                "}, 50);";
        UI.getCurrent().getPage().executeJs(scrollScript);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        getUI().ifPresent(ui -> ui.getPage().executeJs(
            "setTimeout(() => {\n" +
            "    const inp = document.querySelector('.terminal-input-field input');\n" +
            "    if (inp) {\n" +
            "        inp.focus();\n" +
            "        inp.style.caretColor = 'white';\n" +
            "        inp.style.setProperty('caret-color', 'white', 'important');\n" +
            "    }\n" +
            "}, 150);"
        ));
    }
}
