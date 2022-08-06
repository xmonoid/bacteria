package ekosykh.edu.bacteria.logic;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TimerTask;

import static ekosykh.edu.bacteria.logic.Position.BACTERIA_IS_DEAD;
import static ekosykh.edu.bacteria.logic.Position.EMPTY;

public class Dissimilation extends TimerTask {

    private final Position[][] area;

    final Queue<Position[][]> snapshots = new LinkedList<>();

    private int timeToDissimilate;

    public Dissimilation(Position[][] area) {
        this(area, 10);
    }

    public Dissimilation(Position[][] area, int timeToDissimilate) {
        this.area = area;
        this.timeToDissimilate = timeToDissimilate;
    }

    @Override
    public void run() {
        // 1. Снять снапшот поля.
        // 2. Пройтись по полю, найти и сохранить координаты мертвых бактерий
        // 3. Вычесть из текущего снапшота все предыдущие, тогда текущий снапшот будет содержать только
        //    бактерии, умершие на последнем шаге
        // 4. Новый снашпот поставить в очередь на очистку
        // 5. Если в очереди более десяти снапшотов, взять лишние из очереди и очистить соответствующие
        //    точки: они становятся чистыми

        // Сняли снапшот текущего поля
        final Position[][] current;
        synchronized (area) {
            current = Arrays.stream(area).map(Position[]::clone).toArray(Position[][]::new);
        }
        // Убрали все точки, кроме умерших бактерий
        for (int i = 0; i < current.length; i++) {
            for (int j = 0; j < current[i].length; j++) {
                if (current[i][j] != BACTERIA_IS_DEAD) {
                    current[i][j] = EMPTY;
                }
            }
        }
        // Вычитаем из текущего снапшота все предыдущие
        snapshots.forEach( previous -> cleanPositions(current, previous));
        // Новый снапшот ставим в очередь на очстку поля
        snapshots.add(current);

        // Берём устаревшие снапшоты и по ним чистим поле
        if (snapshots.size() > timeToDissimilate) {
            synchronized (area) {
                while (snapshots.size() > timeToDissimilate) {
                    Position[][] toClean = snapshots.remove();
                    cleanPositions(area, toClean);
                }
            }
        }
    }

    private static void cleanPositions(Position[][] shouldBeCleaned, Position[][] whereToClean) {
        for (int i = 0; i < whereToClean.length; i++) {
            for (int j = 0; j < whereToClean[i].length; j++) {
                if (whereToClean[i][j] == BACTERIA_IS_DEAD) {
                    shouldBeCleaned[i][j] = EMPTY;
                }
            }
        }
    }

    public int getTimeToDissimilate() {
        return timeToDissimilate;
    }

    public void setTimeToDissimilate(int timeToDissimilate) {
        this.timeToDissimilate = timeToDissimilate;
    }
}
