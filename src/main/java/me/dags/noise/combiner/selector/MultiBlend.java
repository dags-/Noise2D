package me.dags.noise.combiner.selector;

import java.util.List;
import me.dags.noise.Module;
import me.dags.noise.func.Interpolation;

/**
 * @Author <dags@dags.me>
 */
public class MultiBlend extends Selector {

    private final Node[] nodes;
    private final int maxIndex;
    private final float blend;
    private final float blendRange;

    public MultiBlend(float blend, Interpolation interpolation, Module control, Module... sources) {
        super(control, sources, interpolation);

        float spacing = 1F / (sources.length);
        float radius = spacing / 2F;
        float blendRange = radius * blend;
        float cellRadius = (radius - blendRange) / 2;

        this.blend = blend;
        this.nodes = new Node[sources.length];
        this.maxIndex = sources.length - 1;
        this.blendRange = blendRange;

        for (int i = 0; i < sources.length; i++) {
            float pos = i * spacing + radius;
            float min = i == 0 ? 0 : pos - cellRadius;
            float max = i == maxIndex ? 1 : pos + cellRadius;
            nodes[i] = new Node(sources[i], min, max);
        }
    }

    @Override
    public float selectValue(float x, float y, float selector) {
        int index = Math.round(selector * maxIndex);

        Node min = nodes[index];
        Node max = min;

        if (blendRange == 0) {
            return min.source.getValue(x, y);
        }

        if (selector > min.max) {
            max = nodes[index + 1];
        } else if (selector < min.min) {
            min = nodes[index - 1];
        } else {
            return min.source.getValue(x ,y);
        }

        float alpha = Math.min(1, Math.max(0, (selector - min.max) / blendRange));
        return blendValues(min.source.getValue(x, y), max.source.getValue(x, y), alpha);
    }

    @Override
    public List<?> selectTags(float x, float y, float selector) {
        int index0 = Math.round(selector * maxIndex);
        int index1 = index0;

        Node min = nodes[index0];

        if (blendRange == 0) {
            return min.source.getTags(x, y);
        }

        if (selector > min.max) {
            index1 = index0 + 1;
        } else if (selector < min.min) {
            index0 = index0 - 1;
            min = nodes[index0];
        } else {
            return min.source.getTags(x, y);
        }

        float alpha = Math.min(1, Math.max(0, (selector - min.max) / blendRange));
        return blendTags(index0, index1, alpha);
    }

    protected List<?> blendTags(int index0, int index1, float alpha) {
        return getTags();
    }

    private static class Node {

        private final Module source;
        private final float min;
        private final float max;

        private Node(Module source, float min, float max) {
            this.source = source;
            this.min = Math.max(0, min);
            this.max = Math.min(1, max);
        }

        @Override
        public String toString() {
            return "Slot{" +
                    "min=" + min +
                    ", max=" + max +
                    '}';
        }
    }
}
