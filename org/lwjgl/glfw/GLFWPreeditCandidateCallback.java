/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package org.lwjgl.glfw;

import javax.annotation.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWPreeditCandidateCallbackI;
import org.lwjgl.system.Callback;

public abstract class GLFWPreeditCandidateCallback
extends Callback
implements GLFWPreeditCandidateCallbackI {
    public static GLFWPreeditCandidateCallback create(long functionPointer) {
        GLFWPreeditCandidateCallbackI instance = (GLFWPreeditCandidateCallbackI)Callback.get(functionPointer);
        return instance instanceof GLFWPreeditCandidateCallback ? (GLFWPreeditCandidateCallback)instance : new Container(functionPointer, instance);
    }

    @Nullable
    public static GLFWPreeditCandidateCallback createSafe(long functionPointer) {
        return functionPointer == 0L ? null : GLFWPreeditCandidateCallback.create(functionPointer);
    }

    public static GLFWPreeditCandidateCallback create(GLFWPreeditCandidateCallbackI instance) {
        return instance instanceof GLFWPreeditCandidateCallback ? (GLFWPreeditCandidateCallback)instance : new Container(instance.address(), instance);
    }

    protected GLFWPreeditCandidateCallback() {
        super(CIF);
    }

    GLFWPreeditCandidateCallback(long functionPointer) {
        super(functionPointer);
    }

    public GLFWPreeditCandidateCallback set(long window) {
        GLFW.glfwSetPreeditCandidateCallback(window, this);
        return this;
    }

    private static final class Container
    extends GLFWPreeditCandidateCallback {
        private final GLFWPreeditCandidateCallbackI delegate;

        Container(long functionPointer, GLFWPreeditCandidateCallbackI delegate) {
            super(functionPointer);
            this.delegate = delegate;
        }

        @Override
        public void invoke(long window, int candidates_count, int selected_index, int page_start, int page_size) {
            this.delegate.invoke(window, candidates_count, selected_index, page_start, page_size);
        }
    }
}

