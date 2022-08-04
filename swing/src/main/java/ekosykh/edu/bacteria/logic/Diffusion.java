package ekosykh.edu.bacteria.logic;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TimerTask;

import static ekosykh.edu.bacteria.logic.Position.*;

class Diffusion extends TimerTask {

    private final Position[][] area;

    final Queue<Position[][]> snapshots = new LinkedList<>();

    private int gasTrailLength;

    public Diffusion(Position[][] area) {
        this(area, 5);
    }

    public Diffusion(Position[][] area, int gasTrailLength) {
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
        final Position[][] current;
        synchronized (area) {
            current = Arrays.stream(area).map(Position[]::clone).toArray(Position[][]::new);
        }
        // Убрали все точки, кроме загазованных
        for (int i = 0; i < current.length; i++) {
            for (int j = 0; j < current[i].length; j++) {
                if (current[i][j] != BACTERIA_WAS_HERE) {
                    current[i][j] = EMPTY;
                }
            }
        }
        // Вычитаем из текущего снапшота все предыдущие
        snapshots.forEach( previous -> cleanPositions(current, previous));
        // Новый снапшот ставим в очередь на очстку поля
        snapshots.add(current);

        // Берём устаревшие снапшоты и по ним чистим поле
        if (snapshots.size() > gasTrailLength) {
            synchronized (area) {
                while (snapshots.size() > gasTrailLength) {
                    Position[][] toClean = snapshots.remove();
                    cleanPositions(area, toClean);
                }
            }
        }
    }

    private static void cleanPositions(Position[][] shouldBeCleaned, Position[][] whereToClean) {
        for (int i = 0; i < whereToClean.length; i++) {
            for (int j = 0; j < whereToClean[i].length; j++) {
                if (whereToClean[i][j] == BACTERIA_WAS_HERE) {
                    shouldBeCleaned[i][j] = EMPTY;
                }
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
