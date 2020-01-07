/*
 * Copyright (c) 2018, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tech.mubee.myeditor;

import java.nio.file.Paths;

import io.helidon.media.jsonp.server.JsonSupport;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerConfiguration;
import io.helidon.webserver.StaticContentSupport;
import io.helidon.webserver.WebServer;

public final class Launcher {

  private Launcher() {}

  public static void main(final String... pArgs) {
    startServer();
  }

  static WebServer startServer() {
    ServerConfiguration serverConfig = ServerConfiguration
        .builder()
        .port(8080)
        .build();

    WebServer server = WebServer.create(serverConfig, createRouting());
    server.start()
        .thenAccept(ws -> {
          System.out.println(
              "Open http://localhost:" + ws.port() + " in your browser");
          ws.whenShutdown().thenRun(()
              -> System.out.println("Shut down complete. Good bye!"));
        })
        .exceptionally(t -> {
          System.err.println("Startup failed: " + t.getMessage());
          t.printStackTrace(System.err);
          return null;
        });

    // Server threads are not daemon. No need to block. Just react.
    return server;
  }

  private static Routing createRouting() {
    // TODO: StaticContentSupport is unable to find resources when run under native image.
    /*StaticContentSupport webAppContentSupport = StaticContentSupport
        .builder("web-app", Launcher.class.getClassLoader())
        .welcomeFileName("index.html")
        .build(); */

    return Routing.builder()
        .register(JsonSupport.create())
        // Yeah, we are building a silly file editor that tries to load any JSON file on the filesystem.
        .register("/files", StaticContentSupport.create(Paths.get("/")))
        .register("/", new WebAppService())
        .build();
  }
}
