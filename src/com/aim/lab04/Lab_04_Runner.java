package com.aim.lab04;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.aim.RunData;
import com.aim.TestFrame;
import com.aim.TestFrameConfig;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.satheuristics.genetics.CrossoverHeuristic;
import uk.ac.nott.cs.aim.satheuristics.genetics.PopulationHeuristic;
import uk.ac.nott.cs.aim.satheuristics.genetics.PopulationReplacement;
import uk.ac.nott.cs.aim.searchmethods.SearchMethod;
import uk.ac.nott.cs.aim.statistics.PlotData;
import uk.ac.nott.cs.aim.statistics.XBoxPlot;
import uk.ac.nott.cs.aim.statistics.XLineChart;

public class Lab_04_Runner extends TestFrame {

	private final String BOXPLOT_TITLE;

	public Lab_04_Runner(TestFrameConfig config) {

		super(config);

		Lab04TestFrameConfig oTestConfiguration = (Lab04TestFrameConfig) getTestConfiguration();
		
		BOXPLOT_TITLE = "Results of the " + oTestConfiguration.getConfigurationAsString();
	}

	public List<List<RunData>> runExperimentsForHeuristicId(int heuristicId) {

		Stream<List<RunData>> dat = runUsingExperimentalParallelism(
				rangeAsStream(0, getTestConfiguration().getTotalRuns() - 1)).map(trial -> {

					return this.runExperiment(heuristicId, trial);
				});

		return dat.collect(Collectors.toList());
	}

	public List<RunData> runExperiment(int heuristicId, int trialId) {

		Lab04TestFrameConfig config = (Lab04TestFrameConfig) getTestConfiguration();
		long[] seeds = getExperimentalSeeds();

		long start_time = System.currentTimeMillis();
		// generation based termination
		int POP_SIZE = config.getPopulationSize();
		Random random = new Random(seeds[trialId]);
		SAT sat = new SAT(config.getInstanceId(), Integer.MAX_VALUE, random, POP_SIZE);
		LinkedList<ArrayList<Double>> fitnessTrace = new LinkedList<ArrayList<Double>>();
		for (int i = 0; i < POP_SIZE; i++) {
			fitnessTrace.add(new ArrayList<Double>());
		}

		CrossoverHeuristic crossover = new UniformXO(sat, random);
		PopulationHeuristic mutation = new BitMutation(sat, random);
		PopulationHeuristic localSearch = null;
		switch (config.MODE) {
		case GA:
			localSearch = new NoopHeuristic(sat, random);
			break;
		case MA:
			localSearch = new DavissBitHillClimbingIE(sat, random);
			break;
		default:
			System.err.println("Invalid Mode given in " + config.getClass().getSimpleName());
			System.exit(0);
			break;

		}

		PopulationReplacement replacement = new TransGenerationalReplacementWithElitistReplacement();

		SearchMethod heuristic = new MemeticAlgorithm(sat, random, POP_SIZE, crossover, mutation, localSearch,
				replacement);

		int count = 0;
		
		// add all of population
		Double[] populationFitnesses = IntStream.range(0, POP_SIZE).boxed().map(sat::getObjectiveFunctionValue)
				.sorted().toArray(Double[]::new);

		for (int i = 0; i < populationFitnesses.length; i++) {
			fitnessTrace.get(i).add(populationFitnesses[i]);
		}
					
		while (!sat.hasTimeExpired() && count <= config.MODE.getGenerations()) {

			heuristic.run();

			// add all of population
			populationFitnesses = IntStream.range(0, POP_SIZE).boxed().map(sat::getObjectiveFunctionValue)
					.sorted().toArray(Double[]::new);

			for (int i = 0; i < populationFitnesses.length; i++) {
				fitnessTrace.get(i).add(populationFitnesses[i]);
			}

			count++;
		}

		System.out.println("Time: " + ((System.currentTimeMillis() - start_time) / 1e3));
		System.out.println("Heuristic: " + heuristic.toString());
		System.out.println("Run ID: " + trialId);
		System.out.println("Best Solution Value: " + sat.getBestSolutionValue());
		System.out.println("Best Solution: " + sat.getBestSolutionAsString());
		System.out.println();

		List<RunData> populationRunData = new ArrayList<>();
		for (int i = 0; i < fitnessTrace.size(); i++) {

			String strSeriesName = String.format("%s - Parent #%d", config.MODE.toString(), i);
			populationRunData.add(new RunData(fitnessTrace.get(i), sat.getBestSolutionValue(),
					strSeriesName, heuristicId, trialId, sat.getBestSolutionAsString()));
		}

		return populationRunData;
	}

	public void runTests() {
		
		// execute the experiments (single algorithm so ID is 0)
		// note returns a list of list of RunData since each run contains a population of solutions
		List<List<RunData>> oRunData = runExperimentsForHeuristicId(0);
		
		
		/*
		 * generate boxplots
		 */
		List<PlotData> oPlotData = new ArrayList<PlotData>();

		// get a distinct list of heuristic IDs.
		List<Integer> oHeuristicIds = oRunData.stream().flatMap(x -> x.stream()).map(o -> o.getHeuristicId()).distinct()
				.collect(Collectors.toList());

		// for each heuristic, collate and add the objective values of the best
		// solutions found
		oHeuristicIds.forEach(id -> {

			List<Double> odResults = oRunData.stream().flatMap(x -> x.stream()).filter(f -> f.getHeuristicId() == id)
					.map(f -> f.getBestSolutionValue()).collect(Collectors.toList());

			String strHeuristicName = oRunData.stream().flatMap(x -> x.stream()).filter(f -> f.getHeuristicId() == id).findAny().get()
					.getHeuristicName().substring(0, 2);

			oPlotData.add(new PlotData(odResults, strHeuristicName));
		});

		// create and show the plot
		XBoxPlot.getPlotCreator().createPlot(BOXPLOT_TITLE, "Search Method", "Objective Value", oPlotData);
		
		/*
		 * generate progress plot for best trial
		 */
		
		// create plots for each heuristic
		//create fitness trace for best trial
		for (int id : oHeuristicIds) {

			// set up plot labels
			String strTitle = "Fitness traces of the best trial of the " + getTestConfiguration().getConfigurationAsString();
			String strXLabel = "Iteration";
			String strYLabel = "Objective value";

			// create a list of progress plots for the current heuristic
			List<PlotData> oProgressPlotData = new ArrayList<>();
			
			double bestSolution = oRunData.stream()
				.flatMap(x -> x.stream())
				.filter(f -> f.getHeuristicId() == id)
				.mapToDouble( o -> o.getBestSolutionValue())
				.min().getAsDouble();
			
			List<RunData> bestTrialRunData = oRunData.stream()
				.filter(f -> f.stream().allMatch(p -> p.getHeuristicId() == id))
				.filter(f -> f.stream().anyMatch(p -> p.getBestSolutionValue() == bestSolution))
				.findAny()
				.get();
			
			bestTrialRunData.stream().filter(f -> f.getHeuristicId() == id).forEach(o -> {
				
				oProgressPlotData.add(new PlotData(o.getData(), String.format("Trial #%d - %s", o.getTrialId(), o.getHeuristicName())));
			});

			// creates and shows the plot
			XLineChart.getPlotCreator().createChart(strTitle, strXLabel, strYLabel, oProgressPlotData);
		}

		/*
		 * generate progress plot for worst trial
		 */
		// create plots for each heuristic
		//create fitness trace for best trial
		for (int id : oHeuristicIds) {

			// set up plot labels
			String strTitle = "Fitness traces of the worst trial of the " + getTestConfiguration().getConfigurationAsString();
			String strXLabel = "Iteration";
			String strYLabel = "Objective value";

			// create a list of progress plots for the current heuristic
			List<PlotData> oProgressPlotData = new ArrayList<>();
			
			double worstBestSolution = oRunData.stream()
					// best from each trial
					.mapToDouble(llr -> llr.stream().mapToDouble(o -> o.getBestSolutionValue()).min().getAsDouble())
					// worst of all trials
					.max().getAsDouble();
			
			// find the trial which had the worst best solution
			/// TODO from here
			List<RunData> bestTrialRunData = oRunData.stream()
				.filter(f -> f.stream().allMatch(p -> p.getHeuristicId() == id))
				.filter(f -> f.stream().mapToDouble(o -> o.getBestSolutionValue()).min().getAsDouble() == worstBestSolution)
				.findAny().get();
			
			bestTrialRunData.stream().filter(f -> f.getHeuristicId() == id).forEach(o -> {
				
				oProgressPlotData.add(new PlotData(o.getData(), String.format("Trial #%d - %s", o.getTrialId(), o.getHeuristicName())));
			});

			// creates and shows the plot
			XLineChart.getPlotCreator().createChart(strTitle, strXLabel, strYLabel, oProgressPlotData);
		}
	}

	public static void main(String[] args) {

		TestFrameConfig config = Lab04TestFrameConfig.getInstance();
		TestFrame runner = new Lab_04_Runner(config);
		runner.runTests();
	}

	class Count {
		int i = 0;
		public int getNext() {
			return i++;
		}
	}
}
