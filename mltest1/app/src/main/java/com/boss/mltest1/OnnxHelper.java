package com.boss.mltest1;

import android.content.Context;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.Collections;

import ai.onnxruntime.*;

public class OnnxHelper {
    private OrtEnvironment env;
    private OrtSession session;

    public OnnxHelper(Context context) throws Exception {
        env = OrtEnvironment.getEnvironment();

        // Load ONNX model from assets
        InputStream is = context.getAssets().open("random_forest.onnx");
        byte[] modelBytes = new byte[is.available()];
        is.read(modelBytes);
        is.close();

        session = env.createSession(modelBytes);
    }

    public float[] predict(float[] inputData, int inputSize) throws Exception {
        // Define input shape (batch=1, features=inputSize)
        long[] shape = new long[]{1, inputSize};

        // Wrap input data in FloatBuffer
        FloatBuffer fb = FloatBuffer.wrap(inputData);

        // Create tensor
        OnnxTensor inputTensor = OnnxTensor.createTensor(env, fb, shape);

        // Run inference
        OrtSession.Result results = session.run(
                Collections.singletonMap(session.getInputNames().iterator().next(), inputTensor)
        );

        // Get output
        Object outputObj = results.get(0).getValue();
        float[][] outputArr = (float[][]) outputObj;

        return outputArr[0];
    }
}
