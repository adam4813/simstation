package com.greenstargames.simstation.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.greenstargames.simstation.SimStationGame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(832, 480);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new SimStationGame();
        }
}
