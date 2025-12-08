package com.portfolio.views;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

import java.util.ArrayList;
import java.util.List;

public class MainLayout extends Div implements RouterLayout, AfterNavigationObserver {
    private final Div contentWrapper = new Div();
    private final List<Component> navLinks = new ArrayList<>();
    private final Button soundToggle = new Button();
    private final Button musicToggle = new Button();
    private boolean soundEnabled = true;

    public MainLayout() {
        addClassName("main-wrapper");

        // Header
        Header header = new Header();
        header.addClassName("arcade-header");

        // Brand Logo
        Div brand = new Div();
        brand.addClassName("brand-container");
        
        Span title = new Span("Vaibhav");
        title.addClassName("brand-title");
        
        Span sub = new Span("Developer");
        sub.addClassName("brand-sub");
        
        brand.add(title, sub);
        header.add(brand);

        // Navigation Links
        Div nav = new Div();
        nav.addClassName("nav-links");

        Anchor homeLink = new Anchor("/#intro", "");
        homeLink.addClassName("nav-link-custom");
        homeLink.add(new Icon(VaadinIcon.HOME), new Span("HOME"));
        navLinks.add(homeLink);

        Anchor projectsLink = new Anchor("/#projects", "");
        projectsLink.addClassName("nav-link-custom");
        projectsLink.add(new Icon(VaadinIcon.CODE), new Span("PROJECTS"));
        navLinks.add(projectsLink);

        Anchor aboutLink = new Anchor("/#about", "");
        aboutLink.addClassName("nav-link-custom");
        aboutLink.add(new Icon(VaadinIcon.INFO_CIRCLE_O), new Span("ABOUT ME"));
        navLinks.add(aboutLink);

        Anchor apiConsoleLink = new Anchor("/#api-console", "");
        apiConsoleLink.addClassName("nav-link-custom");
        apiConsoleLink.add(new Icon(VaadinIcon.CONNECT), new Span("API CONSOLE"));
        navLinks.add(apiConsoleLink);

        RouterLink terminalLink = new RouterLink();
        terminalLink.addClassName("nav-link-custom");
        terminalLink.add(new Icon(VaadinIcon.TERMINAL), new Span("TERMINAL"));
        terminalLink.setRoute(TerminalView.class);
        navLinks.add(terminalLink);

        RouterLink contactLink = new RouterLink();
        contactLink.addClassName("nav-link-custom");
        contactLink.addClassName("contact-btn");
        contactLink.add(new Icon(VaadinIcon.ENVELOPE), new Span("CONTACT"));
        contactLink.setRoute(ContactView.class);
        navLinks.add(contactLink);

        nav.add(homeLink, projectsLink, aboutLink, apiConsoleLink, terminalLink, contactLink);
        header.add(nav);

        // Controls
        Div controls = new Div();
        controls.addClassName("header-controls");

        soundToggle.addClassName("arcade-btn");
        soundToggle.setText("SOUND ON");
        soundToggle.addClickListener(e -> {
            soundEnabled = !soundEnabled;
            UI.getCurrent().getPage().executeJs("return window.toggleSound();").then(Boolean.class, muted -> {
                if (muted != null && muted) {
                    soundToggle.setText("SOUND OFF");
                    soundToggle.addClassName("pink");
                } else {
                    soundToggle.setText("SOUND ON");
                    soundToggle.removeClassName("pink");
                }
            });
            playBleepSound();
        });

        musicToggle.addClassName("arcade-btn");
        musicToggle.addClassName("pink");
        musicToggle.setText("MUSIC OFF");
        musicToggle.addClickListener(e -> {
            UI.getCurrent().getPage().executeJs("return window.toggleMusic();").then(Boolean.class, muted -> {
                if (muted != null && muted) {
                    musicToggle.setText("MUSIC OFF");
                    musicToggle.addClassName("pink");
                    musicToggle.removeClassName("cyan");
                } else {
                    musicToggle.setText("MUSIC ON");
                    musicToggle.addClassName("cyan");
                    musicToggle.removeClassName("pink");
                }
            });
            playBleepSound();
        });

        controls.add(soundToggle, musicToggle);
        header.add(controls);

        add(header);

        // Grid Background scrolling line overlay
        Div gridBg = new Div();
        gridBg.addClassName("grid-bg");
        add(gridBg);

        // Glowing outline overlays
        Div purpleOutline = new Div();
        purpleOutline.addClassName("purple-screen-outline");
        
        add(purpleOutline);

        // Content
        contentWrapper.addClassName("view-container");
        add(contentWrapper);

        // Inject synthesizer JavaScript
        injectAudioSynthJS();
    }

    private void injectAudioSynthJS() {
        String js = """
            window.retroAudioCtx = null;
            window.soundMuted = false;
            window.musicMuted = true;
            window.musicStarted = false;

            window.getAudioContext = function() {
                if (!window.retroAudioCtx) {
                    window.retroAudioCtx = new (window.AudioContext || window.webkitAudioContext)();
                }
                if (window.retroAudioCtx.state === 'suspended') {
                    window.retroAudioCtx.resume();
                }
                return window.retroAudioCtx;
            };

            window.playBleep = function() {
                if (window.soundMuted) return;
                try {
                    var ctx = window.getAudioContext();
                    var osc = ctx.createOscillator();
                    var gain = ctx.createGain();
                    osc.connect(gain);
                    gain.connect(ctx.destination);
                    osc.type = 'square';
                    osc.frequency.setValueAtTime(600, ctx.currentTime);
                    osc.frequency.exponentialRampToValueAtTime(150, ctx.currentTime + 0.1);
                    gain.gain.setValueAtTime(0.05, ctx.currentTime);
                    gain.gain.exponentialRampToValueAtTime(0.01, ctx.currentTime + 0.1);
                    osc.start();
                    osc.stop(ctx.currentTime + 0.1);
                } catch(e) {}
            };

            window.playCoin = function() {
                if (window.soundMuted) return;
                try {
                    var ctx = window.getAudioContext();
                    var osc = ctx.createOscillator();
                    var gain = ctx.createGain();
                    osc.connect(gain);
                    gain.connect(ctx.destination);
                    osc.type = 'square';
                    var now = ctx.currentTime;
                    osc.frequency.setValueAtTime(987.77, now);
                    osc.frequency.setValueAtTime(1318.51, now + 0.08);
                    gain.gain.setValueAtTime(0.05, now);
                    gain.gain.setValueAtTime(0.05, now + 0.08);
                    gain.gain.exponentialRampToValueAtTime(0.01, now + 0.35);
                    osc.start();
                    osc.stop(now + 0.35);
                } catch(e) {}
            };

            window.playGameOver = function() {
                if (window.soundMuted) return;
                try {
                    var ctx = window.getAudioContext();
                    var osc = ctx.createOscillator();
                    var gain = ctx.createGain();
                    osc.connect(gain);
                    gain.connect(ctx.destination);
                    osc.type = 'sawtooth';
                    var now = ctx.currentTime;
                    osc.frequency.setValueAtTime(300, now);
                    osc.frequency.linearRampToValueAtTime(150, now + 0.15);
                    osc.frequency.linearRampToValueAtTime(100, now + 0.3);
                    osc.frequency.linearRampToValueAtTime(50, now + 0.6);
                    gain.gain.setValueAtTime(0.08, now);
                    gain.gain.exponentialRampToValueAtTime(0.01, now + 0.6);
                    osc.start();
                    osc.stop(now + 0.6);
                } catch(e) {}
            };

            window.toggleSound = function() {
                window.soundMuted = !window.soundMuted;
                return window.soundMuted;
            };

            // Chiptune Sequencer Data & Logic
            const bassNotes = [
                110.00, 110.00, 164.81, 110.00, // Am
                87.31, 87.31, 130.81, 87.31,     // F
                130.81, 130.81, 196.00, 130.81, // C
                98.00, 98.00, 146.83, 98.00     // G
            ];

            const melodyNotes = [
                440.00, 0, 523.25, 587.33, 659.25, 0, 587.33, 0, 523.25, 440.00, 0, 440.00, 523.25, 0, 0, 0,
                349.23, 0, 440.00, 523.25, 587.33, 0, 523.25, 0, 440.00, 349.23, 0, 349.23, 440.00, 0, 0, 0,
                523.25, 0, 659.25, 783.99, 880.00, 0, 783.99, 0, 659.25, 523.25, 0, 523.25, 659.25, 0, 0, 0,
                392.00, 0, 493.88, 587.33, 659.25, 0, 587.33, 0, 493.88, 392.00, 0, 392.00, 493.88, 587.33, 783.99, 0
            ];

            let currentStep = 0;
            let nextNoteTime = 0.0;
            const stepDuration = 0.125; // 120 BPM tempo
            const scheduleAheadTime = 0.1;
            let timerId = null;

            function scheduleNote(step, time) {
                if (window.musicMuted) return;
                try {
                    var ctx = window.getAudioContext();
                    
                    // 1. Bassline (quarter notes)
                    if (step % 4 === 0) {
                        var beatIndex = Math.floor(step / 4) % 16;
                        var bassNote = bassNotes[beatIndex];
                        var osc = ctx.createOscillator();
                        var gainNode = ctx.createGain();
                        osc.connect(gainNode);
                        gainNode.connect(ctx.destination);
                        
                        osc.type = 'triangle';
                        osc.frequency.setValueAtTime(bassNote, time);
                        
                        gainNode.gain.setValueAtTime(0.02, time); // Low volume
                        gainNode.gain.exponentialRampToValueAtTime(0.0001, time + 0.45);
                        
                        osc.start(time);
                        osc.stop(time + 0.5);
                    }
                    
                    // 2. Melody (16th notes)
                    var melNote = melodyNotes[step % 64];
                    if (melNote > 0 && Math.random() > 0.05) {
                        var osc = ctx.createOscillator();
                        var gainNode = ctx.createGain();
                        osc.connect(gainNode);
                        gainNode.connect(ctx.destination);
                        
                        osc.type = 'square';
                        osc.frequency.setValueAtTime(melNote, time);
                        
                        gainNode.gain.setValueAtTime(0.008, time); // Extremely low volume
                        gainNode.gain.exponentialRampToValueAtTime(0.0001, time + 0.18);
                        
                        osc.start(time);
                        osc.stop(time + 0.25);
                    }
                    
                    // 3. 8-Bit Hi-Hat / Drum ticks
                    if (step % 4 === 2) {
                        playNoiseTick(time, 0.02, 8000, 0.003);
                    } else if (step % 8 === 4) {
                        playNoiseTick(time, 0.06, 1200, 0.006);
                    }
                } catch(e) {}
            }

            function playNoiseTick(time, duration, filterFreq, volume) {
                var ctx = window.getAudioContext();
                var bufferSize = ctx.sampleRate * duration;
                var buffer = ctx.createBuffer(1, bufferSize, ctx.sampleRate);
                var data = buffer.getChannelData(0);
                
                for (var i = 0; i < bufferSize; i++) {
                    data[i] = Math.random() * 2 - 1;
                }
                
                var noiseNode = ctx.createBufferSource();
                noiseNode.buffer = buffer;
                
                var filter = ctx.createBiquadFilter();
                filter.type = 'bandpass';
                filter.frequency.value = filterFreq;
                
                var gainNode = ctx.createGain();
                gainNode.gain.setValueAtTime(volume, time);
                gainNode.gain.exponentialRampToValueAtTime(0.0001, time + duration);
                
                noiseNode.connect(filter);
                filter.connect(gainNode);
                gainNode.connect(ctx.destination);
                
                noiseNode.start(time);
                noiseNode.stop(time + duration);
            }

            function scheduler() {
                var ctx = window.getAudioContext();
                while (nextNoteTime < ctx.currentTime + scheduleAheadTime) {
                    scheduleNote(currentStep, nextNoteTime);
                    nextNoteTime += stepDuration;
                    currentStep++;
                }
            }

            window.startChiptune = function() {
                if (timerId) return;
                try {
                    var ctx = window.getAudioContext();
                    nextNoteTime = ctx.currentTime + 0.05;
                    currentStep = 0;
                    timerId = setInterval(scheduler, 25);
                    window.musicStarted = true;
                } catch(e) {}
            };

            window.stopChiptune = function() {
                if (timerId) {
                    clearInterval(timerId);
                    timerId = null;
                }
                window.musicStarted = false;
            };

            window.toggleMusic = function() {
                window.musicMuted = !window.musicMuted;
                if (window.musicMuted) {
                    window.stopChiptune();
                } else {
                    window.startChiptune();
                }
                return window.musicMuted;
            };

            window.playMeow = function() {
                if (window.soundMuted) return;
                try {
                    var ctx = window.getAudioContext();
                    var osc = ctx.createOscillator();
                    var gain = ctx.createGain();
                    osc.connect(gain);
                    gain.connect(ctx.destination);
                    osc.type = 'triangle';
                    var now = ctx.currentTime;
                    osc.frequency.setValueAtTime(800, now);
                    osc.frequency.exponentialRampToValueAtTime(1200, now + 0.15);
                    osc.frequency.exponentialRampToValueAtTime(1000, now + 0.3);
                    gain.gain.setValueAtTime(0.04, now);
                    gain.gain.exponentialRampToValueAtTime(0.001, now + 0.3);
                    osc.start();
                    osc.stop(now + 0.3);
                } catch(e) {}
            };

            window.spawnHearts = function(containerId) {
                const container = document.getElementById(containerId);
                if (!container) return;
                
                if (window.playMeow) {
                    window.playMeow();
                } else if (window.playBleep) {
                    window.playBleep();
                }
                
                const heartCount = 10;
                for (let i = 0; i < heartCount; i++) {
                    setTimeout(() => {
                        const heart = document.createElement('div');
                        heart.className = 'pixel-heart';
                        heart.innerText = '❤';
                        
                        const left = Math.random() * 80 + 10;
                        const top = Math.random() * 40 + 40;
                        const scale = Math.random() * 0.6 + 0.8;
                        const duration = Math.random() * 1.5 + 1.5;
                        
                        heart.style.left = left + '%';
                        heart.style.top = top + '%';
                        heart.style.setProperty('--drift-x', (Math.random() * 40 - 20) + 'px');
                        heart.style.animation = `heartFloat ${duration}s ease-out forwards`;
                        heart.style.transform = `scale(${scale})`;
                        
                        container.appendChild(heart);
                        
                        setTimeout(() => {
                            heart.remove();
                        }, duration * 1000);
                    }, i * 150);
                }
            };

            // Autoplay trigger on user interaction
            function autoPlayHandler() {
                if (!window.musicMuted && !window.musicStarted) {
                    window.startChiptune();
                }
                document.removeEventListener('click', autoPlayHandler);
                document.removeEventListener('keydown', autoPlayHandler);
            }
            document.addEventListener('click', autoPlayHandler);
            document.addEventListener('keydown', autoPlayHandler);
            """;
        
        UI.getCurrent().getPage().executeJs(js);
    }

    public void playBleepSound() {
        UI.getCurrent().getPage().executeJs("if(window.playBleep) window.playBleep();");
    }

    public void playCoinSound() {
        UI.getCurrent().getPage().executeJs("if(window.playCoin) window.playCoin();");
    }

    public void playGameOverSound() {
        UI.getCurrent().getPage().executeJs("if(window.playGameOver) window.playGameOver();");
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        contentWrapper.removeAll();
        contentWrapper.getElement().appendChild(content.getElement());
        playBleepSound(); // Play bleep when navigating
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        // Highlight active link
        String path = event.getLocation().getPath();
        for (Component link : navLinks) {
            String linkPath = link instanceof RouterLink ? ((RouterLink) link).getHref() : ((Anchor) link).getHref();
            if (path.equals(linkPath) || (path.isEmpty() && "/#intro".equals(linkPath)) || (path.equals("terminal") && linkPath.equals("terminal"))) {
                link.getElement().getClassList().add("active");
            } else {
                link.getElement().getClassList().remove("active");
            }
        }
    }
}
