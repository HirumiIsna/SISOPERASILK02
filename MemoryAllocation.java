import java.util.Arrays;
import java.util.Scanner;

public class MemoryAllocation {

    static int[] blockSizes;
    static int[] processSizes;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("--- SIMULASI ALGORITMA ALOKASI MEMORI (FIRST-FIT, BEST-FIT, WORST-FIT ---");

        // Input blok memori
        System.out.print("\nMasukkan jumlah blok memori : ");
        int numBlocks = sc.nextInt();
        blockSizes = new int[numBlocks];
        System.out.println("Masukkan ukuran tiap blok memori:");
        for (int i = 0; i < numBlocks; i++) {
            System.out.print("Blok " + (i + 1) + " : ");
            blockSizes[i] = sc.nextInt();
        }

        // Input proses
        System.out.print("\nMasukkan jumlah proses : ");
        int numProcesses = sc.nextInt();
        processSizes = new int[numProcesses];
        System.out.println("Masukkan ukuran tiap proses:");
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Proses P" + (i + 1) + " : ");
            processSizes[i] = sc.nextInt();
        }

        sc.close();

        // Ringkasan input
        System.out.println("\nUkuran Blok Memori : " + Arrays.toString(blockSizes));
        System.out.println("Ukuran Proses      : " + Arrays.toString(processSizes));

        firstFit();
        bestFit();
        worstFit();
    }

    static void firstFit() {
        int[] blocks = blockSizes.clone();
        boolean[] used = new boolean[blocks.length];
        int[] alloc = new int[processSizes.length];
        Arrays.fill(alloc, -1);

        System.out.println("\n--- FIRST FIT ---");

        for (int i = 0; i < processSizes.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (!used[j] && blocks[j] >= processSizes[i]) {
                    alloc[i] = j;
                    blocks[j] -= processSizes[i];
                    used[j] = true; 
                    break;
                }
            }
        }

        printResult(alloc, blocks, used);
    }

    static void bestFit() {
        int[] blocks = blockSizes.clone();
        boolean[] used = new boolean[blocks.length];
        int[] alloc = new int[processSizes.length];
        Arrays.fill(alloc, -1);

        System.out.println("\n--- BEST FIT ---");

        for (int i = 0; i < processSizes.length; i++) {
            int bestIdx = -1;
            for (int j = 0; j < blocks.length; j++) {
                if (!used[j] && blocks[j] >= processSizes[i]) {
                    if (bestIdx == -1 || blocks[j] < blocks[bestIdx]) {
                        bestIdx = j;
                    }
                }
            }
            if (bestIdx != -1) {
                alloc[i] = bestIdx;
                blocks[bestIdx] -= processSizes[i];
                used[bestIdx] = true;
            }
        }

        printResult(alloc, blocks, used);
    }

    static void worstFit() {
        int[] blocks = blockSizes.clone();
        boolean[] used = new boolean[blocks.length];
        int[] alloc = new int[processSizes.length];
        Arrays.fill(alloc, -1);

        System.out.println("\n--- WORST FIT ---");

        for (int i = 0; i < processSizes.length; i++) {
            int worstIdx = -1;
            for (int j = 0; j < blocks.length; j++) {
                if (!used[j] && blocks[j] >= processSizes[i]) {
                    if (worstIdx == -1 || blocks[j] > blocks[worstIdx]) {
                        worstIdx = j;
                    }
                }
            }
            if (worstIdx != -1) {
                alloc[i] = worstIdx;
                blocks[worstIdx] -= processSizes[i];
                used[worstIdx] = true;
            }
        }

        printResult(alloc, blocks, used);
    }

    // ===================== PRINT HASIL =====================
    static void printResult(int[] alloc, int[] blocks, boolean[] used) {
        System.out.printf("%-12s %-15s %-15s %-10s%n","Proses", "Ukuran Proses", "Blok Dialokasi", "Status");
        System.out.println("------------------------------------------------------------");

        for (int i = 0; i < processSizes.length; i++) {
            if (alloc[i] != -1) {
                System.out.printf("%-12s %-15d %-15d %-10s%n",
                        "P" + (i + 1), processSizes[i], alloc[i] + 1, "BERHASIL");
            } else {
                System.out.printf("%-12s %-15d %-15s %-10s%n",
                        "P" + (i + 1), processSizes[i], "-", "GAGAL");
            }
        }

        System.out.println("\nSisa Memori Tiap Blok:");
        System.out.printf("%-10s %-15s %-15s %-10s%n", "Blok", "Ukuran Awal", "Sisa", "Status");
        System.out.println("------------------------------------------");
        for (int i = 0; i < blocks.length; i++) {
            String status = used[i] ? "TERPAKAI" : "KOSONG";
            System.out.printf("%-10s %-15d %-15d %-10s%n", "Blok " + (i + 1), blockSizes[i], blocks[i], status);
        }
    }
}
