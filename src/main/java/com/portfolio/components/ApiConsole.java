package com.portfolio.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;

@Tag("api-console")
public class ApiConsole extends Component {
    
    public ApiConsole() {
        Div frame = new Div();
        frame.addClassName("api-console-frame");
        
        Div header = new Div();
        header.addClassName("api-console-header");
        Span title = new Span("MAINFR_API CLIENT v1.0.0");
        title.addClassName("console-title");
        header.add(title);
        frame.add(header);
      
        Div workspace = new Div();
        workspace.addClassName("console-workspace");
        
        Div sidebar = new Div();
        sidebar.addClassName("console-sidebar");
        
        Span collHeader = new Span("COLLECTIONS");
        collHeader.addClassName("sidebar-header");
        sidebar.add(collHeader);
        
        Div endpointsList = new Div();
        endpointsList.addClassName("console-endpoints-list");
        
        endpointsList.add(createSidebarItem("GET", "/api/v1/profile", "active"));
        endpointsList.add(createSidebarItem("GET", "/api/v1/skills", ""));
        endpointsList.add(createSidebarItem("GET", "/api/v1/projects", ""));
        endpointsList.add(createSidebarItem("GET", "/api/v1/resume", ""));
        endpointsList.add(createSidebarItem("POST", "/api/v1/contact", ""));
        
        sidebar.add(endpointsList);
        workspace.add(sidebar);
        
        Div editorPane = new Div();
        editorPane.addClassName("console-editor");
        
        Div requestBar = new Div();
        requestBar.addClassName("console-request-bar");
        
        Span methodTag = new Span("GET");
        methodTag.setId("consoleRequestType");
        methodTag.addClassName("console-method-tag");
        methodTag.addClassName("get");
        
        Span urlField = new Span("/api/v1/profile");
        urlField.setId("consoleRequestUrl");
        urlField.addClassName("console-url-display");
        
        Span sendBtn = new Span("SEND");
        sendBtn.setId("consoleSendBtn");
        sendBtn.addClassName("console-send-btn");
        
        requestBar.add(methodTag, urlField, sendBtn);
        editorPane.add(requestBar);
        
        Div bodyPane = new Div();
        bodyPane.setId("consoleRequestBodyPane");
        bodyPane.addClassName("console-body-pane");
        bodyPane.getStyle().set("display", "none");
        
        Span bodyHeader = new Span("JSON REQUEST BODY");
        bodyHeader.addClassName("body-pane-header");
        
        com.vaadin.flow.component.html.NativeLabel bodyLabel = new com.vaadin.flow.component.html.NativeLabel();
        bodyLabel.setFor("consoleRequestBodyInput");
        bodyLabel.getStyle().set("display", "none");
        
        com.vaadin.flow.component.html.Input bodyInput = new com.vaadin.flow.component.html.Input();
        bodyInput.setId("consoleRequestBodyInput");
        bodyInput.setType("textarea");
        bodyInput.addClassName("console-body-input");
        bodyInput.setValue("{\n  \"name\": \"Guest Pilot\",\n  \"email\": \"guest@domain.com\",\n  \"message\": \"Hello from TTY1 console!\"\n}");
        
        bodyPane.add(bodyHeader, bodyLabel, bodyInput);
        editorPane.add(bodyPane);
        
        Span responseHeader = new Span("RESPONSE DETAILS");
        responseHeader.addClassName("response-section-header");
        editorPane.add(responseHeader);
        
        Div metaBar = new Div();
        metaBar.addClassName("console-meta-bar");
        
        Span statusNode = new Span("STATUS: ---");
        statusNode.setId("consoleStatus");
        statusNode.addClassName("meta-stat");
        
        Span timeNode = new Span("TIME: ---");
        timeNode.setId("consoleTime");
        timeNode.addClassName("meta-stat");
        
        Span sizeNode = new Span("SIZE: ---");
        sizeNode.setId("consoleSize");
        sizeNode.addClassName("meta-stat");
        
        metaBar.add(statusNode, timeNode, sizeNode);
        editorPane.add(metaBar);
        
        Div outputScreen = new Div();
        outputScreen.addClassName("console-output-screen");
        
        com.vaadin.flow.component.HtmlContainer codePre = new com.vaadin.flow.component.HtmlContainer("pre");
        codePre.setId("consoleJsonViewer");
        codePre.addClassName("json-viewer");
        codePre.getElement().setProperty("innerHTML", "<span class='json-placeholder'>CLICK 'SEND' TO EXECUTE PACKET DISPATCH...</span>");
        
        outputScreen.add(codePre);
        editorPane.add(outputScreen);
        
        workspace.add(editorPane);
        frame.add(workspace);
        
        getElement().appendChild(frame.getElement());
        
        setupConsoleJS();
    }
    
    private Div createSidebarItem(String method, String url, String state) {
        Div item = new Div();
        item.addClassName("sidebar-item");
        if (!state.isEmpty()) {
            item.addClassName(state);
        }
        item.getElement().setAttribute("data-method", method);
        item.getElement().setAttribute("data-url", url);
        
        Span methodSpan = new Span(method);
        methodSpan.addClassName("item-method");
        methodSpan.addClassName(method.toLowerCase());
        
        Span urlSpan = new Span(url);
        urlSpan.addClassName("item-url");
        
        item.add(methodSpan, urlSpan);
        return item;
    }
    
    private void setupConsoleJS() {
        String js = """
                const consoleRoot = $0;
                
                const sidebarItems = consoleRoot.querySelectorAll('.sidebar-item');
                const reqType = consoleRoot.querySelector('#consoleRequestType');
                const reqUrl = consoleRoot.querySelector('#consoleRequestUrl');
                const bodyPane = consoleRoot.querySelector('#consoleRequestBodyPane');
                const bodyInput = consoleRoot.querySelector('#consoleRequestBodyInput');
                const sendBtn = consoleRoot.querySelector('#consoleSendBtn');
                
                const metaStatus = consoleRoot.querySelector('#consoleStatus');
                const metaTime = consoleRoot.querySelector('#consoleTime');
                const metaSize = consoleRoot.querySelector('#consoleSize');
                const jsonViewer = consoleRoot.querySelector('#consoleJsonViewer');
                
                let activeMethod = 'GET';
                let activeUrl = '/api/v1/profile';
                
                function highlightJSON(jsonObj) {
                    let json = JSON.stringify(jsonObj, null, 2);
                    json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
                    return json.replace(/("(\\\\u[a-zA-Z0-9]{4}|\\\\[^u]|[^\\\\"])*"(\\s*:)?|\\b(true|false|null)\\b|-?\\d+(?:\\.\\d*)?(?:[eE][+-]?\\d+)?)/g, function (match) {
                        let cls = 'number';
                        if (/^"/.test(match)) {
                            if (/:$/.test(match)) {
                                cls = 'key';
                            } else {
                                cls = 'string';
                            }
                        } else if (/true|false/.test(match)) {
                            cls = 'boolean';
                        } else if (/null/.test(match)) {
                            cls = 'null';
                        }
                        return '<span class="json-' + cls + '">' + match + '</span>';
                    });
                }
                
                sidebarItems.forEach(item => {
                    item.addEventListener('click', () => {
                        sidebarItems.forEach(i => i.classList.remove('active'));
                        item.classList.add('active');
                        
                        activeMethod = item.getAttribute('data-method');
                        activeUrl = item.getAttribute('data-url');
                        
                        reqType.innerText = activeMethod;
                        reqType.className = 'console-method-tag ' + activeMethod.toLowerCase();
                        reqUrl.innerText = activeUrl;
                        
                        if (activeMethod === 'POST') {
                            bodyPane.style.display = 'flex';
                        } else {
                            bodyPane.style.display = 'none';
                        }
                        
                        metaStatus.innerText = 'STATUS: ---';
                        metaStatus.className = 'meta-stat';
                        metaTime.innerText = 'TIME: ---';
                        metaSize.innerText = 'SIZE: ---';
                        jsonViewer.innerHTML = "<span class='json-placeholder'>CLICK 'SEND' TO EXECUTE PACKET DISPATCH...</span>";
                    });
                });
                
                sendBtn.addEventListener('click', () => {
                    jsonViewer.innerHTML = "<span class='json-loading'>DISPATCHING API SIGNAL REQUEST...</span>";
                    
                    const t0 = performance.now();
                    const options = {
                        method: activeMethod,
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    };
                    
                    if (activeMethod === 'POST') {
                        try {
                            const parsed = JSON.parse(bodyInput.value);
                            options.body = JSON.stringify(parsed);
                        } catch(err) {
                            metaStatus.innerText = 'STATUS: 400 BAD REQUEST';
                            metaStatus.className = 'meta-stat status-err';
                            metaTime.innerText = 'TIME: 0 ms';
                            metaSize.innerText = 'SIZE: 0 B';
                            jsonViewer.innerHTML = `<span class="json-error">MALFORMED REQUEST BODY:\\n${err.message}</span>`;
                            return;
                        }
                    }
                    
                    fetch(activeUrl, options)
                        .then(res => {
                            const t1 = performance.now();
                            const elapsed = Math.round(t1 - t0);
                            
                            const statusText = `STATUS: ${res.status} ${res.statusText || (res.status === 200 ? 'OK' : '')}`;
                            metaStatus.innerText = statusText;
                            metaStatus.className = 'meta-stat ' + (res.status >= 200 && res.status < 300 ? 'status-ok' : 'status-err');
                            metaTime.innerText = `TIME: ${elapsed} ms`;
                            
                            return res.json().then(data => {
                                const jsonSize = new Blob([JSON.stringify(data)]).size;
                                metaSize.innerText = `SIZE: ${jsonSize} B`;
                                jsonViewer.innerHTML = highlightJSON(data);
                            });
                        })
                        .catch(err => {
                            metaStatus.innerText = 'STATUS: OFFLINE';
                            metaStatus.className = 'meta-stat status-err';
                            metaTime.innerText = 'TIME: ---';
                            metaSize.innerText = 'SIZE: ---';
                            jsonViewer.innerHTML = `<span class="json-error">SIGNAL PACKET FAILED TO DISPATCH:\\n${err.message}</span>`;
                        });
                });
                """;
        UI.getCurrent().getPage().executeJs(js, getElement());
    }
}
