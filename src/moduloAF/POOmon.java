package moduloAF;

import java.awt.Image;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import javax.imageio.ImageIO;

import moduloAGame.Env;
import moduloAGame.Mediator;
import moduloAGame.POOmonBehavior;

public class POOmon implements POOmonBehavior {

	private String name;

	private int activations;

	private int energy;

	private String history;

	private Image image;

	private int highestEnergy;

	private LocalDateTime highestEnergyTime;

	private int wins;

	private Env environment;

	private Mediator mediator;
	// utilizado somente para controle do log
	private boolean takedDamage;
	// utilizado somente para controle do log
	private boolean attacked;
	// utilizado somente para controle do log
	private int shift = 0;

	public POOmon(String name, String history, Env env) {
		setName(name);
		setHistory(history);
		setEnvironment(env);
		setEnergy(500);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setActivations(int activations) {
		this.activations = activations;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setHighestEnergy(int highestEnergy) {
		this.highestEnergy = highestEnergy;
	}

	public void setHighestEnergyTime(LocalDateTime highestEnergyTime) {
		this.highestEnergyTime = highestEnergyTime;
	}

	public void setEnvironment(Env env) {
		this.environment = env;
	}

	public void addWin() {
		this.wins += 1;
	}

	@Override
	public int getActivations() {
		return this.activations;
	}

	@Override
	public int getEnergy() {
		return this.energy;
	}

	@Override
	public Env getEnvironment() {
		return this.environment;
	}

	@Override
	public int getHighestEnergy() {
		return this.highestEnergy;
	}

	@Override
	public LocalDateTime getHighestEnergyTime() {
		return this.highestEnergyTime;
	}

	@Override
	public String getHistory() {
		return this.history;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getWins() {
		return this.wins;
	}

	@Override
	public boolean isAlive() {
		if (this.energy > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Image getImage() {
		return this.image;
	}

	@Override
	public void loser() {
		try {
			FileWriter fw = new FileWriter(mediator.getLogFilesPath().toFile());
			BufferedWriter writer = new BufferedWriter(fw);
			writer.write("Derrota");
			writer.close();
		} catch (IOException ieo) {
			ieo.printStackTrace();
		}
	}

	@Override
	public void winner() {
		addWin();
		try {
			FileWriter fw = new FileWriter(mediator.getLogFilesPath().toFile());
			BufferedWriter writer = new BufferedWriter(fw);
			writer.write("Vitória");
			writer.close();
		} catch (IOException ieo) {
			ieo.printStackTrace();
		}
	}

	@Override
	public void attack(POOmonBehavior arg0, Env arg1) {
		try {
			FileWriter fw = new FileWriter(mediator.getLogFilesPath().toFile());
			BufferedWriter writer = new BufferedWriter(fw);
			if (!this.takedDamage && !this.attacked) {
				this.shift = 0;
				printOpponent(arg0, writer);
			}
			int damage = 0;
			int damageboost = 0;
			String typeAttackText = "";
			Random randomInt = new Random();
			int typeAttack = randomInt.nextInt(3 - 1 + 1) + 1;
			int energySpent = 0;
			if (typeAttack == 3) {
				energySpent = randomInt.nextInt(200 - 100 + 1) + 100;
				damage = Math.round(energySpent * 1.50f);
				typeAttackText = "Cruel";
			} else if (typeAttack == 2) {
				energySpent = randomInt.nextInt(99 - 40 + 1) + 40;
				damage = Math.round(energySpent);
				typeAttackText = "Agressivo";
			} else {
				damage = 30;
				typeAttackText = "Básico";
			}
			if (typeAttack == 3 || typeAttack == 2) {
				this.energy -= damage;
			}
			if (arg1.equals(this.environment)) {
				damageboost = Math.round(damage * 0.20f);
			}
			if (this.shift == 0) {
				printVitalEnergy(writer);
			}
			arg0.takeDamage(damage + damageboost, arg1);
			writer.write("Ataque Efetuado: " + typeAttackText + " " + damage + " (" + (damage + damageboost) + ") - "
					+ arg1.toString() + "(-" + energySpent + ")");
			writer.close();
			if (this.shift == 0) {
				restoreEnergy(50);
			}
		} catch (IOException ieo) {
			ieo.printStackTrace();
		}
		setImage();
	}

	private void setImage() {
		try {
			Image image;
			if (!isAlive()) {
				image = ImageIO.read(getClass().getResource(this.name + "/Morto.png"));
			} else if (getEnergy() > 350) {
				image = ImageIO.read(getClass().getResource(this.name + "/Saudavel.png"));
			} else if (getEnergy() > 151) {
				image = ImageIO.read(getClass().getResource(this.name + "/Cansado.png"));
			} else {
				image = ImageIO.read(getClass().getResource(this.name + "/Exausto.png"));
			}
			this.image = image;
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	@Override
	public void restoreEnergy(int arg0) {
		try {
			FileWriter fw = new FileWriter(mediator.getLogFilesPath().toFile());
			BufferedWriter writer = new BufferedWriter(fw);
			writer.write("Energia recebida: " + arg0);
			writer.close();
		} catch (IOException ieo) {
			ieo.printStackTrace();
		}
		setEnergy(this.energy + arg0);
		if (this.energy > this.highestEnergy) {
			this.highestEnergy = energy;
			this.highestEnergyTime = LocalDateTime.now();
		}
	}

	@Override
	public void setMediator(Mediator arg0) {
		this.mediator = arg0;
	}

	@Override
	public void takeDamage(int arg0, Env arg1) {
		try {
			FileWriter fw = new FileWriter(mediator.getLogFilesPath().toFile());
			BufferedWriter writer = new BufferedWriter(fw);
			if (!this.takedDamage && !this.attacked) {
				this.shift = 1; 
				/*Professor, não consegui imaginar como eu imprimiria o oponente no cenário onde
					o POOmon começa recebendo o dano
				*/
			}
			int damage = arg0;
			if (arg1.equals(this.environment)) {
				damage = Math.round(arg0 * 0.90f);
			}
			if (this.shift == 1) {
				printVitalEnergy(writer);
			}
			this.energy -= damage;

			writer.write("Ataque Recebido: " + arg0 + " - " + arg1.toString() + "(-" + damage + ")");
			writer.close();
			if (this.shift == 1) {
				restoreEnergy(50);
			}
		} catch (IOException ieo) {
			ieo.printStackTrace();
		}
		setImage();
	}

	private void printVitalEnergy(BufferedWriter writer) throws IOException {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		writer.write("Minha energia vital: " + this.energy + " - "
				+ LocalDate.parse(LocalDate.now().toString(), dateFormatter) + " - "
				+ LocalDateTime.parse(LocalDateTime.now().toString(), timeFormatter));
	}

	private void printOpponent(POOmonBehavior pooMon, BufferedWriter writer) throws IOException {
		String env = "";
		switch (pooMon.getEnvironment().ordinal()) {
		case 0:
			env = "Água";
			break;
		case 1:
			env = "Ar";
			break;
		case 2:
			env = "Terra";
			break;
		}
		writer.write("Openente: " + pooMon.getName() + " - " + env);
	}
}
