/*
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

import io.helidon.webserver.Routing;
import io.helidon.webserver.Service;

/**
 * Just reads index.html from the resources folder.
 * This should be superseded by StaticContentSupport once we figure out the bug while
 * running StaticContentSupport with resources under a native image.
 */
public class WebAppService
    implements Service {

  @Override
  public void update(Routing.Rules pRules) {
    pRules.get("/", (req, resp) -> {
      try {
        byte[] indexHtml = this.getClass().getResourceAsStream("/web-app/index.html").readAllBytes();
        resp.status(200).send(indexHtml);
      } catch (Exception ex) {
        ex.printStackTrace();
        resp.status(500).send();
      }
    });
  }
}

