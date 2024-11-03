package com.myvg.myvg;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;

public class MyvgApplication extends Application{

	private ConfigurableApplicationContext springContext;

	@Override
	public void init() throws Exception {
		ApplicationContextInitializer<GenericApplicationContext> initializer = 
			ac -> {
					ac.registerBean(Application.class, () -> MyvgApplication.this);
					ac.registerBean(Parameters.class, MyvgApplication.this::getParameters);
					ac.registerBean(HostServices.class, this::getHostServices);
				};

		springContext = new SpringApplicationBuilder()
				.sources(SpringBoot.class)
				.initializers(initializer)
				.run(getParameters().getRaw().toArray(new String[0]));
	}


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.springContext.publishEvent(new StageReadyEvent(primaryStage));
    }

    @Override
    public void stop() throws Exception {
        this.springContext.close();
		Platform.exit();
    }

public class StageReadyEvent extends ApplicationEvent {

	public Stage getStage() { return Stage.class.cast(getSource()); }	

	public StageReadyEvent(Stage stage) {
		super(stage);
	}
}
}