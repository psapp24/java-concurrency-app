package service;

import model.Template;

public class TemplateService {

    public Template getTemplate() {

        sleep(2000);

        System.out.println(
                Thread.currentThread().getName()
                        + " -> Template Service");

        return new Template(
                "Welcome to ChatGPT Learning");
    }

    private void sleep(long ms) {

        try {

            Thread.sleep(ms);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }
}
