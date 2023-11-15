package conexao;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;

public class ConexaoSlack {
    private static String webHookUrl = "https://hooks.slack.com/services/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";
    private static String oAthuToken = "xoxb-6070510759798-6077244697730-eyiI1r6E53hBwir8KCloemVY";
    private static String calnalSlack = "canal-alertas";

    public void enviarMensagemCpu(Double usoCpu) {
        try {
            StringBuilder msgbuilder = new StringBuilder();
            msgbuilder.append(usoCpu);

            Payload payload = Payload.builder().channel(calnalSlack).text(msgbuilder.toString()).build();

            WebhookResponse webhookResponse = Slack.getInstance().send(webHookUrl, payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarMensagemRam(Double usoRam) {
        try {
            StringBuilder msgbuilder = new StringBuilder();
            msgbuilder.append(usoRam);

            Payload payload = Payload.builder().channel(calnalSlack).text(msgbuilder.toString()).build();

            WebhookResponse webhookResponse = Slack.getInstance().send(webHookUrl, payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarMensagemDisco(Integer usoAtualDisco) {
        try {
            StringBuilder msgbuilder = new StringBuilder();
            msgbuilder.append(usoAtualDisco);

            Payload payload = Payload.builder().channel(calnalSlack).text(msgbuilder.toString()).build();

            WebhookResponse webhookResponse = Slack.getInstance().send(webHookUrl, payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarMensagemRede(Double rede) {
        try {
            StringBuilder msgbuilder = new StringBuilder();
            msgbuilder.append(rede);

            Payload payload = Payload.builder().channel(calnalSlack).text(msgbuilder.toString()).build();

            WebhookResponse webhookResponse = Slack.getInstance().send(webHookUrl, payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}