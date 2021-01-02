package com.simulationFrameworkDT.simulation.tools;

import java.security.InvalidParameterException;
import java.util.HashMap;

import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.distribution.CauchyDistribution;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.distribution.EnumeratedRealDistribution;
import org.apache.commons.math3.distribution.FDistribution;
import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.distribution.TriangularDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.apache.commons.math3.distribution.WeibullDistribution;
import org.apache.commons.math3.random.EmpiricalDistribution;

public class ProbabilisticDistribution implements IDistribution{

	private AbstractRealDistribution distribution;
	
	public void typeHandler(String type, HashMap<String, Object> args) {
    	
        AbstractRealDistribution dist;
        
        if(type.equals("FDistribution")){
            double numeratorDegreesOfFreedom = (Double)args.get("numeratorDegreesOfFreedom");
            double denominatorDegreesOfFreedom = (Double)args.get("denominatorDegreesOfFreedom");
            dist = new FDistribution(numeratorDegreesOfFreedom, denominatorDegreesOfFreedom);
        } else if(type.equals("NormalDistribution")){
            double mean = (Double)args.get("mean");
            double sd = (Double)args.get("sd");
            dist = new NormalDistribution(mean, sd);
        } else if(type.equals("CauchyDistribution")){
            double median = (Double)args.get("median");
            double scale = (Double)args.get("scale");
            dist = new CauchyDistribution(median, scale);
        } else if(type.equals("ChiSquaredDistribution")){
            double degreesOfFreedom = (Double)args.get("degreesOfFreedom");
            dist = new ChiSquaredDistribution(degreesOfFreedom);
        } else if(type.equals("BetaDistribution")){
            double alpha = (Double)args.get("alpha");
            double beta = (Double)args.get("beta");
            dist = new BetaDistribution(alpha, beta);
        } else if(type.equals("EnumeratedRealDistribution")){
            double[] singletons = (double[])args.get("singletons");
            double[] probabilities = (double[])args.get("probabilities");
            dist = new EnumeratedRealDistribution(singletons, probabilities);
        } else if(type.equals("TDistribution")){
            double degreesOfFreedom = (Double)args.get("degreesOfFreedom");
            dist = new TDistribution(degreesOfFreedom);
        } else if(type.equals("TriangularDistribution")){
            double a = (Double)args.get("a");
            double b = (Double)args.get("b");
            double c = (Double)args.get("c");
            dist = new TriangularDistribution(a, c, b);
        } else if(type.equals("UniformRealDistribution")){
            double lower = (Double)args.get("lower");
            double upper = (Double)args.get("upper");
            dist = new UniformRealDistribution(lower, upper);
        } else if(type.equals("WeibullDistribution")){
            double alpha = (Double)args.get("alpha");
            double beta = (Double)args.get("beta");
            dist = new WeibullDistribution(alpha, beta);
        } else if(type.equals("EmpiricalDistribution")){
            int binCount = (Integer)args.get("binCount");
            dist = new EmpiricalDistribution(binCount);	
        } else if(type.equals("LogNormalDistribution")){
        	double scale = (Double)args.get("scale");
        	double shape = (Double)args.get("shape");
        	dist = new LogNormalDistribution(scale, shape);
        } else {	
            throw new InvalidParameterException("Invalid type, cannot create a distribution object with the given type");
        }
        distribution = dist;
    }

    public double getSample(){
    	return distribution.sample();
    }
    
	public void WeibullDistribution(double alpha, double beta) {
		
		String type = "WeibullDistribution";
        HashMap<String, Object> params = new HashMap<>();
        
        params.put("alpha", alpha); //Shape
        params.put("beta", beta); //Scale
		
		typeHandler(type, params);
	}
	
	public void LogNormalDistribution (double scale, double shape) {
		
		String type = "LogNormalDistribution";
		HashMap<String, Object> params = new HashMap<>();
        
        params.put("shape", shape);
        params.put("scale", scale);
		
		typeHandler(type, params);
	}
}
