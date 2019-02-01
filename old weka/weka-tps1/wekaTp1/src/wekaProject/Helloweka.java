package wekaProject;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.converters.ArffSaver;

import weka.associations.Apriori;

import weka.*;

public class Helloweka {

	public static void main(String[] args) throws Exception {
		 
		// converte CSV to Arff fomat 
		// ici si convArrfSaver.setInstances(data); ne fonctionne pas alors il faut chercher un .jar perdu
		
		CSVLoader loader = new CSVLoader();
		
		loader.setSource(new File("5-conference.csv"));

		Instances data = loader.getDataSet();
		
		ArffSaver convArrfSaver = new ArffSaver(); 
		convArrfSaver.setInstances(data);
		convArrfSaver.setFile(new File("weka-tps1/wekaTp1/testR.arff"));
		convArrfSaver.setInstances(data);	
		convArrfSaver.writeBatch(); // coper le fichier csv dans le arff
		
		
		DataSource LaSource = new DataSource("weka-tps1/wekaTp1/testR.arff");
		Instances Info = LaSource.getDataSet();
		
		/****Clustering using K-Means and EM *******/
		// distance function --> euclidean fucntion genre par default
		// on peut par exemple creer une nouvelle fucntion de distance exo 3 ou 4
		// on utilise le fichier creer ici testR.arff
		
		// SimpleKMeans Clusters
		weka.clusterers.SimpleKMeans modelClusKM = new weka.clusterers.SimpleKMeans();
		modelClusKM.setNumClusters(2);
		modelClusKM.buildClusterer(Info);
		System.out.println(modelClusKM);
		
		// EM Clusters
		weka.clusterers.EM modelClusEM = new weka.clusterers.EM();
		modelClusEM.setNumClusters(2);
		modelClusEM.buildClusterer(Info);
		//System.out.println(modelClusEM);
		
		/************ classifiers *************/
		// binary classifications (femme / homme .. or 0 /1 ....)
		// multi-class peu se transformer en binary classification
		
		Info.setClassIndex(Info.numAttributes()-1); // derniere ligne
		//creation et le build d une classifier
		weka.classifiers.bayes.NaiveBayes modelClassifNB = new weka.classifiers.bayes.NaiveBayes();
		modelClassifNB.buildClassifier(Info);
		//System.out.println(modelClassifNB.getCapabilities().toString());
		
		//on peu utilise le J48 digraph on peu ajouter des options ou pas
		weka.classifiers.trees.J48 modelClassifArbre = new weka.classifiers.trees.J48();
		modelClassifArbre.buildClassifier(Info);
		//System.out.println(modelClassifArbre.getCapabilities().toString());
		
		/********** association ******/
		DataSource LaSource2 = new DataSource("/Users/abdel/Desktop/weka-tps1/wekaTp1/trains.arff");
		Instances Info2 = LaSource2.getDataSet();
		
		//trouver des associations des correlations dans le data
		Apriori modelAsso = new Apriori();
		modelAsso.buildAssociations(Info2);
		//System.out.println(modelAsso);
		
	}

	private static String String(Path path) {
		// TODO Auto-generated method stub
		return null;
	}
}