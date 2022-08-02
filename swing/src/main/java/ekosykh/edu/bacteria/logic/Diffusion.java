package ekosykh.edu.bacteria.logic;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TimerTask;

class Diffusion extends TimerTask {

    private final int[][] area;

    final Queue<int[][]> snapshots = new LinkedList<>();

    private int gasTrailLength;

    public Diffusion(int[][] area) {
        this(area, 5);
    }

    public Diffusion(int[][] area, int gasTrailLength) {
        this.area = area;
        this.gasTrailLength = gasTrailLength;
    }

    @Override
    public void run() {
        // 1. Снять снапшот поля.
        // 2. Пройтись по полю, найти и сохранить координаты загазованных точек
        // 3. Вычесть из текущего снапшота все предыдущие, тогда текущий снапшот будет содержать только
        //    точки, загазованные на последнем шаге
        // 4. Новый снашпот поставить в очередь на очистку
        // 5. Если в очереди более десяти снапшотов, взять лишние из очереди и очистить соответствующие
        //    точки: они становятся чистыми

        // Сняли снапшот текущего поля
        final int[][] current;
        synchronized (area) {
            current = Arrays.stream(area).map(int[]::clone).toArray(int[][]::new);
        }
        // Убрали все точки, кроме загазованных
        for (int i = 0; i < current.length; i++) {
            for (int j = 0; j < current[i].length; j++) {
                if (current[i][j] != Position.BACTERIA_WAS_HERE.getValue()) {
                    current[i][j] = Position.EMPTY.getValue();
                }
            }
        }
        // Вычитаем из текущего снапшота все предыдущие
        snapshots.forEach( previous -> subtractAreas(current, previous));
        // Новый снапшот ставим в очередь на очстку поля
        snapshots.add(current);

        // Берём устаревшие снапшоты и по ним чистим поле
        if (snapshots.size() > gasTrailLength) {
            synchronized (area) {
                while (snapshots.size() > gasTrailLength) {
                    int[][] toClean = snapshots.remove();
                    subtractAreas(area, toClean);
                }
            }
        }
    }

    static void subtractAreas(int[][] minuend, int[][] subtrahend) {
        for (int i = 0; i < minuend.length; i++) {
            for (int j = 0; j < minuend[i].length; j++) {
                minuend[i][j] -= subtrahend[i][j];
            }
        }
    }

    public int getGasTrailLength() {
        return gasTrailLength;
    }

    public void setGasTrailLength(int gasTrailLength) {
        this.gasTrailLength = gasTrailLength;
    }
}
