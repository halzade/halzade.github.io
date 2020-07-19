//package com.pieceofinfinity.fractal.engine;
//
//import Time;
//import Mathematician;
//import HH;
//import org.jocl.*;
//
//class GPUAbstract {
//
//	protected cl_context context;
//	protected cl_command_queue commandQueue;
//
//	protected cl_program program;
//	protected cl_kernel kernel;
//	protected cl_device_id device;
//
//	protected void init() {
//
//		// Time.m("GPU: init start");
//
//		// The platform, device type and device number
//		// that will be used
//		final int platformIndex = 0;
//		final int deviceIndex = 0;
//
//		// Enable exceptions and subsequently omit error checks in this sample
//		CL.setExceptionsEnabled(true);
//
//		// Obtain the number of platforms
//		int numPlatformsArray[] = new int[3];
//		CL.clGetPlatformIDs(0, null, numPlatformsArray);
//		int numPlatforms = numPlatformsArray[0];
//
//		// Obtain a platform ID
//		cl_platform_id platforms[] = new cl_platform_id[numPlatforms];
//		CL.clGetPlatformIDs(platforms.length, platforms, null);
//		cl_platform_id platform = platforms[platformIndex];
//
//		// Initialize the context properties
//		cl_context_properties contextProperties = new cl_context_properties();
//		contextProperties.addProperty(CL.CL_CONTEXT_PLATFORM, platform);
//
//		// Obtain the number of devices for the platform
//		int numDevicesArray[] = new int[1];
//		CL.clGetDeviceIDs(platform, CL.CL_DEVICE_TYPE_ALL, 0, null, numDevicesArray);
//		int numDevices = numDevicesArray[0];
//
//		// Obtain a device ID
//		cl_device_id devices[] = new cl_device_id[numDevices];
//		CL.clGetDeviceIDs(platform, CL.CL_DEVICE_TYPE_ALL, numDevices, devices, null);
//		this.device = devices[deviceIndex];
//
//		for (int i = 0; i < numDevices; i++) {
//			String deviceName = getString(devices[i], CL.CL_DEVICE_NAME);
//			Time.m("Device " + (i + 1) + " of " + numDevices + ": " + deviceName);
//		}
//
//		// Create a context for the selected device
//		context = CL.clCreateContext(
//				contextProperties, 1, new cl_device_id[]{device},
//				null, null, null);
//
//		// Create a command-queue for the selected device
//		commandQueue = CL.clCreateCommandQueue(context, device, 0, null);
//		// cl_queue_properties properties = new cl_queue_properties();
//		// commandQueue = CL.clCreateCommandQueueWithProperties(context, device, properties, null);
//
//		 /*
//		 GIGABYTE GTX 960 MINI Gaming 2GB
//         GIGABYTE GT 730 Ultra Durable 2 2GB
//         GIGABYTE GT 640 NVIDIA GeForce GT640
//         */
//
//		// final int cudaCount = 1024 + 384 + 384; // 1792
//		// Time.m("CUDA: " + cudaCount);
//
//
//		final String PROGRAM_SOURCE_CODE =
//				/*
//				"inline int domainToScreenRe(__private double re) {\n"
//				+ "     int xx = (int) floor((" + Application.RESOLUTION_X + " * (re - " + areaDomain.centerRe() + ") / " + Application.RESOLUTION_X + ") + " + (Application.RESOLUTION_X / 2) + ");\n"
//				+ "     if (xx >= " + Application.RESOLUTION_X + " ) {\n"
//				+ "        xx = " + (Application.RESOLUTION_X - 1) + ";\n"
//				+ "     }\n"
//				+ "     if (xx < 0) {\n"
//				+ "        xx = 0;\n"
//				+ "     }\n"
//				+ "     return xx;\n"
//				+ "}\n"
//				+ "inline int domainToScreenIm(__private double imX) {\n"
//				+ "     int yy = (int) floor((" + Application.RESOLUTION_Y + " * (" + areaDomain.centerIm() + " - imX) / " + Application.RESOLUTION_X + ") + " + (Application.RESOLUTION_Y / 2) + ");\n"
//				+ "     if (yy >= " + Application.RESOLUTION_Y + " ) {\n"
//				+ "         yy = " + (Application.RESOLUTION_Y - 1) + ";\n"
//				+ "     }\n"
//				+ "     if (yy < 0) {\n"
//				+ "         yy = 0;\n"
//				+ "     }\n"
//				+ "     return yy;\n"
//				+ "}\n"
//				*/
//				"__kernel void calculateFractalValues(\n"
//						+ "             __global double *originReT,\n"
//						+ "             __global double *originImX,\n"
//						+ "             __global double *originImY,\n"
//						+ "             __global double *originImZ,\n"
//						+ "             __global double *lastVisitedReT,\n"
//						+ "             __global double *lastVisitedImX,\n"
//						+ "             __global double *lastVisitedImY,\n"
//						+ "             __global double *lastVisitedImZ,\n"
//						+ "             __global int *iterationsToRun) {\n"
//						+ "     int gid = get_global_id(0);\n"
//						+ "     double reT = lastVisitedReT[gid];\n"
//						+ "     double imX = lastVisitedImX[gid];\n"
//						+ "     double imY = lastVisitedImY[gid];\n"
//						+ "     double imZ = lastVisitedImZ[gid];\n"
//						+ "     int iterator = 0;\n"
//						+ "     double reTNew;\n"
//						+ "     double oReT = originReT[gid];\n"
//						+ "     double oImX = originImX[gid];\n"
//						+ "     double oImY = originImY[gid];\n"
//						+ "     double oImZ = originImZ[gid];\n"
//						+ "     int itr = iterationsToRun[gid];\n"
//						+ "     double tTemp;\n"
//						+ "     double xTemp;\n"
//						+ "     double yTemp;\n"
//						+ "     double zTemp;\n"
//						+ "     double reT2 = reT * reT;\n"
//						+ "     double imX2 = imX * imX;\n"
//
//						+ "     while ((" + HH.quadranceGPU() + ") < 4  && iterator < itr) {\n"
//
//						/* ******** Magic here */
//						+ Mathematician.magicGPU()
//
//						+ "         iterator++;\n"
//						+ "     }\n"
//						//		Use iterationsToRun to return DivergedIn
//						+ "     iterationsToRun[gid] = iterator;\n"
//						//      To continue with calculation from here next time
//						+ "     lastVisitedReT[gid] = reT;\n"
//						+ "     lastVisitedImX[gid] = imX;\n"
//						+ "     lastVisitedImY[gid] = imY;\n"
//						+ "     lastVisitedImZ[gid] = imZ;\n"
//						+ "}\n";
//
//		Time.m("1");
//		program = CL.clCreateProgramWithSource(context, 1, new String[]{PROGRAM_SOURCE_CODE}, null, null);
//		Time.m("2");
//		CL.clBuildProgram(program, 0, null, null, null, null);
//		// int[] errorKernel = new int[100];
//		Time.m("3");
//		kernel = CL.clCreateKernel(program, "calculateFractalValues", null);
//		Time.m("4");
//
//		// Time.m("GPU: init end");
//
//	}
//
//	protected String kb(long bytes) {
//		return ((long) Math.floor(bytes / 1024)) + " Kb";
//	}
//
//	/**
//	 * Returns the value of the device info parameter with the given name
//	 *
//	 * @param device    The device
//	 * @param paramName The parameter name
//	 * @return The value
//	 */
//	private String getString(cl_device_id device, int paramName) {
//		long size[] = new long[1];
//		CL.clGetDeviceInfo(device, paramName, 0, null, size);
//		byte buffer[] = new byte[(int) size[0]];
//		CL.clGetDeviceInfo(device, paramName, buffer.length, Pointer.to(buffer), null);
//		return new String(buffer, 0, buffer.length - 1);
//	}
//}
