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

