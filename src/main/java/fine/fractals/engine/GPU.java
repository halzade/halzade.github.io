//package com.pieceofinfinity.fractal.engine;
//
//import Application;
//import Time;
//import CalculationData;
//import com.pieceofinfinity.fractals.data.Data;
//import com.pieceofinfinity.fractals.data.ElementComparator;
//import Log;
//import Element;
//import org.jocl.CL;
//import org.jocl.Pointer;
//import org.jocl.Sizeof;
//import org.jocl.cl_mem;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class GPU extends GPUAbstract {
//
//	// private int computationRun = 0;
//
//	private long executionTime;
//	/* Maximum allowed GPU execution time 60s */
//	private final long EXECUTION_TIME_MAX = 60 * 1000;
//	private int SPLIT_TO = 1;
//
//	public GPU() {
//		init();
//	}
//
//	public CalculationData filter(ArrayList<Element> fractalDomainList, CalculationData cd) {
//
//		/* Size of work unit is decided in Mandelbrot */
//		int calculationSize = fractalDomainList.size();
//		// time.now("GPU filter start " + Data.par(calculationSize));
//
//		// input values
//		double[] originReT = new double[calculationSize];
//		double[] originImX = new double[calculationSize];
//		double[] originImY = new double[calculationSize];
//		double[] originImZ = new double[calculationSize];
//		// input and output values
//		double[] lastVisitedReT = new double[calculationSize];
//		double[] lastVisitedImX = new double[calculationSize];
//		double[] lastVisitedImY = new double[calculationSize];
//		double[] lastVisitedImZ = new double[calculationSize];
//		int[] iterationsToRun = new int[calculationSize];
//
//
//		/* I want to split fractalDomainList on half if calculation takes longer then 30s */
//		List<ArrayList<Element>> parts = chopped(fractalDomainList, fractalDomainList.size() / SPLIT_TO);
//
//		boolean canIncrease = true;
//		// time.now("GPU prepare" + Data.spar(parts.size()));
//		for (ArrayList<Element> part : parts) {
//			int gid = 0;
//			for (Element el : part) {
//				originReT[gid] = el.origin.reT;
//				originImX[gid] = el.origin.imX;
//				originImY[gid] = el.origin.imY;
//				originImZ[gid] = el.origin.imZ;
//				lastVisitedReT[gid] = el.lastVisited.reT;
//				lastVisitedImX[gid] = el.lastVisited.imX;
//				lastVisitedImY[gid] = el.lastVisited.imY;
//				lastVisitedImZ[gid] = el.lastVisited.imZ;
//				iterationsToRun[gid] = Application.iteration_Max_Full - el.getLastIteration();
//				gid++;
//			}
//
//			Pointer pointerOriginReT = Pointer.to(originReT);
//			Pointer pointerOriginImX = Pointer.to(originImX);
//			Pointer pointerOriginImY = Pointer.to(originImY);
//			Pointer pointerOriginImZ = Pointer.to(originImZ);
//			Pointer pointerLastVisitedReT = Pointer.to(lastVisitedReT);
//			Pointer pointerLastVisitedImX = Pointer.to(lastVisitedImX);
//			Pointer pointerLastVisitedImY = Pointer.to(lastVisitedImY);
//			Pointer pointerLastVisitedImZ = Pointer.to(lastVisitedImZ);
//			Pointer pointerIterationsToRun = Pointer.to(iterationsToRun);
//
//			/* Allocate the memory objects for the input- and output data */
//			/* Origin used as calculation input */
//			int memOriginsSize = Sizeof.cl_double * calculationSize;
//
//			/* Origin used as calculation input */
//			cl_mem memOriginReT = CL.clCreateBuffer(context,
//					CL.CL_MEM_READ_ONLY | CL.CL_MEM_COPY_HOST_PTR,
//					memOriginsSize, pointerOriginReT, null);
//			cl_mem memOriginImX = CL.clCreateBuffer(context,
//					CL.CL_MEM_READ_ONLY | CL.CL_MEM_COPY_HOST_PTR,
//					memOriginsSize, pointerOriginImX, null);
//			cl_mem memOriginImY = CL.clCreateBuffer(context,
//					CL.CL_MEM_READ_ONLY | CL.CL_MEM_COPY_HOST_PTR,
//					memOriginsSize, pointerOriginImY, null);
//			cl_mem memOriginImZ = CL.clCreateBuffer(context,
//					CL.CL_MEM_READ_ONLY | CL.CL_MEM_COPY_HOST_PTR,
//					memOriginsSize, pointerOriginImZ, null);
//
//
//			/* Element used as calculation input for continuation of calculation */
//			/* And to return elements for continuation of calculation next time */
//			int memLastVisitedSize = Sizeof.cl_double * calculationSize;
//
//			cl_mem memLastVisitedReT = CL.clCreateBuffer(context,
//					CL.CL_MEM_READ_WRITE | CL.CL_MEM_COPY_HOST_PTR,
//					memLastVisitedSize, pointerLastVisitedReT, null);
//			/* Element used as calculation input for continuation of calculation */
//			/* And to return elements for continuation of calculation next time */
//			cl_mem memLastVisitedImX = CL.clCreateBuffer(context,
//					CL.CL_MEM_READ_WRITE | CL.CL_MEM_COPY_HOST_PTR,
//					memLastVisitedSize, pointerLastVisitedImX, null);
//			cl_mem memLastVisitedImY = CL.clCreateBuffer(context,
//					CL.CL_MEM_READ_WRITE | CL.CL_MEM_COPY_HOST_PTR,
//					memLastVisitedSize, pointerLastVisitedImY, null);
//			cl_mem memLastVisitedImZ = CL.clCreateBuffer(context,
//					CL.CL_MEM_READ_WRITE | CL.CL_MEM_COPY_HOST_PTR,
//					memLastVisitedSize, pointerLastVisitedImZ, null);
//
//			/* Iteration of calculation to run */
//			/* And to return when element diverged */
//			int memIterationsToRunSize = Sizeof.cl_int * calculationSize;
//			cl_mem memIterationsToRun = CL.clCreateBuffer(context,
//					CL.CL_MEM_READ_WRITE | CL.CL_MEM_COPY_HOST_PTR,
//					memIterationsToRunSize,
//					pointerIterationsToRun, null);
//			// long totalMemory = memOriginReSize + memOriginImSize + memLastVisitedReSize + memLastVisitedImSize + memOriginDivergedInSize;
//
//			// Set the arguments for the kernel
//			CL.clSetKernelArg(kernel, 0, Sizeof.cl_mem, Pointer.to(memOriginReT));
//			CL.clSetKernelArg(kernel, 1, Sizeof.cl_mem, Pointer.to(memOriginImX));
//			CL.clSetKernelArg(kernel, 2, Sizeof.cl_mem, Pointer.to(memOriginImY));
//			CL.clSetKernelArg(kernel, 3, Sizeof.cl_mem, Pointer.to(memOriginImZ));
//
//			CL.clSetKernelArg(kernel, 4, Sizeof.cl_mem, Pointer.to(memLastVisitedReT));
//			CL.clSetKernelArg(kernel, 5, Sizeof.cl_mem, Pointer.to(memLastVisitedImX));
//			CL.clSetKernelArg(kernel, 6, Sizeof.cl_mem, Pointer.to(memLastVisitedImY));
//			CL.clSetKernelArg(kernel, 7, Sizeof.cl_mem, Pointer.to(memLastVisitedImZ));
//
//			CL.clSetKernelArg(kernel, 8, Sizeof.cl_mem, Pointer.to(memIterationsToRun));
//
//			// Set the work-item dimensions
//			long global_work_size[] = new long[]{calculationSize};
//			// long local_work_size[] = new long[]{1};
//			long local_work_size[] = null;
//
//			// time.now("GPU Enqueue and Run and Read");
//			executionTime = Time.ctimss();
//			// Execute the kernel
//			CL.clEnqueueNDRangeKernel(commandQueue, kernel, 1, null, global_work_size, local_work_size, 0, null, null);
//
//
//			try {
//				CL.clEnqueueReadBuffer(commandQueue,
//						memLastVisitedReT, CL.CL_TRUE, 0,
//						memLastVisitedSize,
//						pointerLastVisitedReT, 0, null, null);
//				// time.now("GPU Run OK and read");
//				executionTime = Time.ctimss() - executionTime;
//
//				// time.now("GPU read 1");
//				CL.clEnqueueReadBuffer(commandQueue,
//						memLastVisitedImX, CL.CL_TRUE, 0,
//						memLastVisitedSize,
//						pointerLastVisitedImX, 0, null, null);
//				CL.clEnqueueReadBuffer(commandQueue,
//						memLastVisitedImY, CL.CL_TRUE, 0,
//						memLastVisitedSize,
//						pointerLastVisitedImY, 0, null, null);
//				CL.clEnqueueReadBuffer(commandQueue,
//						memLastVisitedImZ, CL.CL_TRUE, 0,
//						memLastVisitedSize,
//						pointerLastVisitedImZ, 0, null, null);
//
//				// time.now("GPU read 2");
//				CL.clEnqueueReadBuffer(commandQueue,
//						memIterationsToRun, CL.CL_TRUE, 0,
//						memIterationsToRunSize,
//						pointerIterationsToRun, 0, null, null);
//				// time.now("GPU read OK - " + kb(memIterationsToRunSize));
//			} catch (Throwable t) {
//				Log.severe(t);
//				throw t;
//			}
//
//
//			// time.red("XXXXXXXX GUP executionTime: " + executionTime + Data.spar(SPLIT_TO) + " XXXXXXXX");
//			if (executionTime > EXECUTION_TIME_MAX) {
//				if (canIncrease) {
//					if (SPLIT_TO <= 420) {
//						SPLIT_TO++;
//					}
//					canIncrease = false;
//					time.now("SPLIT_TO " + SPLIT_TO);
//				}
//			}
//
//
//			// time.now("GPU organize");
//			gid = 0;
//			int divergedIn;
//			int shouldDivergeIn;
//			for (Element el : part) {
//				divergedIn = iterationsToRun[gid];
//				shouldDivergeIn = Application.iteration_Max_Full - el.getLastIteration();
//				/* Elements with paths for Design */
//				if (divergedIn < shouldDivergeIn) {
//					if (divergedIn > Application.iterationMinGet()) {
//						cd.domainGPUFiltered.add(el);
//					}
//					/* Element diverged */
//					el.setValue(divergedIn);
//					/* Don't set last iteration; I will need test if it was 0 bellow. It is set in last else */
//					/* This state may latter change to hibernatedFinishedInside */
//					el.setHibernatedFinished();
//					cd.newDiverged++;
//					cd.newDivergedNow++;
//					if (divergedIn > cd.newMax) {
//						cd.newMax = divergedIn;
//					}
//					if (divergedIn < cd.newMin) {
//						cd.newMin = divergedIn;
//					}
//				} else {
//					/* Element didn't diverge yet */
//					el.setLastVisited(lastVisitedReT[gid], lastVisitedImX[gid], lastVisitedImY[gid], lastVisitedImZ[gid]);
//					/* Don't set Value. It would mess up black elements inside */
//					el.setLastIteration(divergedIn);
//					el.setBlack();
//				}
//				gid++;
//			}
//			// time.now("GPU organize OK");
//			// Release memory objects
//			CL.clReleaseMemObject(memOriginReT);
//			CL.clReleaseMemObject(memOriginImX);
//			CL.clReleaseMemObject(memOriginImY);
//			CL.clReleaseMemObject(memOriginImZ);
//			CL.clReleaseMemObject(memLastVisitedReT);
//			CL.clReleaseMemObject(memLastVisitedImX);
//			CL.clReleaseMemObject(memLastVisitedImY);
//			CL.clReleaseMemObject(memLastVisitedImZ);
//			CL.clReleaseMemObject(memIterationsToRun);
//
//			// Time.waitHere();
//
//			/* Don't release these if GPU use is repeated */
//			// CL.clReleaseKernel(kernel);
//			// CL.clReleaseProgram(program);
//			// CL.clReleaseCommandQueue(commandQueue);
//			// CL.clReleaseContext(context);
//			// CL.clReleaseDevice(device);
//
//		}
//
//		// time.now("GPU: filter end");
//		return cd;
//	}
//
//
//	private List<ArrayList<Element>> chopped(ArrayList<Element> list, final int L) {
//		List<ArrayList<Element>> parts = new ArrayList<>();
//		final int N = list.size();
//		for (int i = 0; i < N; i += L) {
//			ArrayList<Element> part = new ArrayList<>(list.subList(i, Math.min(N, i + L)));
//			/* Order elements to have longest executions first */
//			part.sort(new ElementComparator());
//			parts.add(part);
//		}
//		return parts;
//	}
//
//}