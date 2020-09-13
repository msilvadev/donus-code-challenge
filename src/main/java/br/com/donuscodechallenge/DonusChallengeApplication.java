package br.com.donuscodechallenge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class DonusChallengeApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(DonusChallengeApplication.class);

	public static void main(String[] args) {
		try{
			LOGGER.info("Initializing DonusChallenge Application...");
			new SpringApplicationBuilder()
					.bannerMode(Banner.Mode.LOG)
					.sources(DonusChallengeApplication.class)
					.run(args);
			LOGGER.info("DonusChallenge has completed startup");
			System.gc();
		} catch (Exception e) {
			LOGGER.error("Exception when starting DonusChallenge", e);
			System.exit(1);
		}
	}
}
