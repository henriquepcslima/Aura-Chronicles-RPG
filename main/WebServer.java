package main;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import engine.CommandParser;
import entities.Player;
import world.GameMap;

import java.io.IOException;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class WebServer {
    private static GameMap map;
    private static Player player;
    private static CommandParser parser;

    public static void main(String[] args) throws IOException {
        
        map = new GameMap();
        parser = new CommandParser();
        player = new Player("Ardyn", 100);

        
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        
        server.createContext("/", new HomeHandler());       
        server.createContext("/cmd", new CommandHandler()); 

        server.setExecutor(null); 
        System.out.println("Servidor rodando! Abra no navegador: http://localhost:8000");
        server.start();
    }

    
    static class HomeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String html = "<!DOCTYPE html>" +
                    "<html lang='pt-br'>" +
                    "<head>" +
                    "<meta charset='UTF-8'>" +
                    "<title>Aura Chronicles</title>" +
                    "<link href='https://fonts.googleapis.com/css2?family=VT323&display=swap' rel='stylesheet'>" +
                    "<style>" +
                    "body { background-color: #050505; color: #cccccc; font-family: 'VT323', monospace; font-size: 22px; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }" +
                    "#game-container { width: 900px; max-width: 95%; border: 2px solid #333; border-radius: 10px; background-color: #111; box-shadow: 0 0 20px rgba(0, 255, 255, 0.1); padding: 20px; }" +
                    "h1 { text-align: center; color: #00ffff; text-shadow: 0 0 5px #00ffff; margin-top: 0; }" +
                    
                    
                    "#output { border: 1px solid #222; height: 500px; overflow-y: auto; padding: 15px; background-color: #000; color: #eee; white-space: pre-wrap; margin-bottom: 15px; font-family: 'VT323', monospace; scrollbar-width: thin; scrollbar-color: #444 #000; }" +
                    "#output::-webkit-scrollbar { width: 8px; }" +
                    "#output::-webkit-scrollbar-track { background: #000; }" +
                    "#output::-webkit-scrollbar-thumb { background-color: #444; border-radius: 4px; }" +
                    
                    
                    ".battle-img { " +
                    "   display: block; " +
                    "   max-width: 200px; " +
                    "   max-height: 200px; " +
                    "   margin: 10px 0; " +
                    "   border: 2px solid #444; " +
                    "   border-radius: 8px; " +
                    "   box-shadow: 0 0 10px rgba(255, 0, 0, 0.3); " +
                    "}" +

                    
                    "#input-area { display: flex; gap: 10px; }" +
                    "input { flex-grow: 1; padding: 15px; background: #0f0f0f; color: #00ff00; border: 1px solid #333; border-radius: 5px; font-family: 'VT323', monospace; font-size: 20px; outline: none; }" +
                    "input:focus { border-color: #007acc; }" +
                    "button { padding: 10px 25px; background: #007acc; color: white; border: none; border-radius: 5px; font-family: 'VT323', monospace; font-size: 20px; cursor: pointer; transition: 0.2s; }" +
                    "button:hover { background: #005fa3; transform: scale(1.05); }" +
                    
                    
                    "#controls { margin-top: 10px; display: grid; grid-template-columns: repeat(4, 1fr); gap: 5px; }" +
                    ".action-btn { padding: 8px; background: #222; color: #aaa; border: 1px solid #444; cursor: pointer; font-family: 'VT323'; font-size: 18px; }" +
                    ".action-btn:hover { background: #333; color: white; }" +

                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div id='game-container'>" +
                        "<h1>AURA CHRONICLES</h1>" +
                        "<div id='output'>Conectando ao servidor... Digite 'olhar' para iniciar.</div>" +
                        
                        "<div id='input-area'>" +
                            "<input type='text' id='cmd' placeholder='Digite seu comando...' autofocus autocomplete='off'>" +
                            "<button onclick='sendCommand()'>ENVIAR</button>" +
                        "</div>" +
                        
                        
                        /*
                        "<div id='controls'>" +
                            "<button class='action-btn' onclick=\"sendCommand('norte')\">Norte</button>" +
                            "<button class='action-btn' onclick=\"sendCommand('sul')\">Sul</button>" +
                            "<button class='action-btn' onclick=\"sendCommand('leste')\">Leste</button>" +
                            "<button class='action-btn' onclick=\"sendCommand('oeste')\">Oeste</button>" +
                            "<button class='action-btn' onclick=\"sendCommand('atacar')\">Atacar</button>" +
                            "<button class='action-btn' onclick=\"sendCommand('curar')\">Curar</button>" +
                            "<button class='action-btn' onclick=\"sendCommand('inventario')\">Mochila</button>" +
                            "<button class='action-btn' onclick=\"sendCommand('olhar')\">Olhar</button>" +
                        "</div>" +
                        */
                        
                    "</div>" +
                    "<script>" +
                    "var input = document.getElementById('cmd');" +
                    "input.addEventListener('keypress', function(event) { if (event.key === 'Enter') sendCommand(); });" +
                    "function sendCommand(btnCmd) {" +
                    "  var cmd = btnCmd || input.value;" +
                    "  if(!cmd) return;" +
                    "  input.value = '';" +
                    "  addText('> ' + cmd, '#888');" +
                    "  fetch('/cmd?c=' + encodeURIComponent(cmd))" +
                    "    .then(response => response.text())" +
                    "    .then(text => addText(text));" +
                    "}" +
                    "function addText(text, color) {" +
                    "  var div = document.getElementById('output');" +
                    "  var span = document.createElement('div');" +
                    "  span.innerHTML = text;" +
                    "  if(color) span.style.color = color;" +
                    "  div.appendChild(span);" +
                    "  div.scrollTop = div.scrollHeight;" +
                    "}" +
                    "</script>" +
                    "</body>" +
                    "</html>";

            byte[] bytes = html.getBytes(StandardCharsets.UTF_8);
            t.sendResponseHeaders(200, bytes.length);
            OutputStream os = t.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }

    
    static class CommandHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String query = t.getRequestURI().getQuery();
            String command = "";
            if (query != null && query.contains("c=")) {
                command = query.split("c=")[1];
                command = java.net.URLDecoder.decode(command, StandardCharsets.UTF_8);
            }

            System.out.println("Recebido: " + command);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            PrintStream oldOut = System.out;
            
            
            PrintStream captureStream = new PrintStream(buffer, true, StandardCharsets.UTF_8);
            System.setOut(captureStream); 

            try {
                parser.processCommand(command, player, map);
                System.out.flush(); 
                captureStream.flush();
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                e.printStackTrace();
            } finally {
                System.setOut(oldOut);
                captureStream.close();
            }

            String response = buffer.toString(StandardCharsets.UTF_8);
            if (response.isEmpty()) response = "...";

            byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
            t.sendResponseHeaders(200, bytes.length);
            OutputStream os = t.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }
}
