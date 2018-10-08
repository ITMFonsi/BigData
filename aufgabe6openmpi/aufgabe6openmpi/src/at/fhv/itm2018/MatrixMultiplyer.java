package at.fhv.itm2018;

import mpi.*;
import mpi.MPIException;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;

public class MatrixMultiplyer {
    // mpi-related values
    int myrank = 0;;
    int nprocs = 0;

    // matrices
    static double  a[] = null; // array must be one dimensional in mpiJava.
    static double b[] = null; // array must be one dimensional in mpiJava.
    static double c[] = null; // array must be one dimensional in mpiJava.

    // message component
    int averows;               // average #rows allocated to each rank
    int extra;                 // extra #rows allocated to some ranks
    int offset[] = new int[1]; // offset in row
    int rows[] = new int[1];   // the actual # rows allocated to each rank
    int mtype;                 // message type (tagFromMaster or tagFromWorker )

    final static int tagFromMaster = 1;
    final static int tagFromWorker = 2;
    final static int master = 0;

    // print option
    boolean printOption = false; // print out all array contents if true

    /**
     * Initializes matrices.
     * @param size the size of row/column for each matrix
     */
    private void init( int size ) {
        // Initialize matrices

        for ( int i = 0; i < size; i++ )
            for ( int j = 0; j < size; j++ )
                a[i * size + j] = i + j;       // a[i][j] = i + j;
        for ( int i = 0; i < size; i++ )
            for ( int j = 0; j < size; j++ )
                b[i * size + j] = i - j;       // b[i][j] = i - j;
        for ( int i = 0; i < size; i++ )
            for ( int j = 0; j < size; j++ )
                c[i * size + j] = 0;           // c[i][j] = 0
    }

    /**
     * Computes a multiplication for my allocated rows.
     * @param size the size of row/column for each matrix
     */
    private void compute( int size ) {

        for ( int k = 0; k < size; k++ )
            for ( int i = 0; i < rows[0]; i++ )
                for ( int j = 0; j < size; j++ ) {
                    // c[i][k] += a[i][j] * b[j][k]
                    c[i * size + k] += a[i * size + j] * b[j *size + k];
                }
    }

    /**
     * Prints out all elements of a given array.
     * @param array an array of doubles to print out
     */
    private void print( double array[] ) {
        if ( myrank == 0 && printOption == true ) {
            int size = ( int )Math.sqrt( ( double )array.length );
            for ( int i = 0; i < size; i++ )
                for ( int j = 0; j < size; j++ ) {
                    System.out.println( "[" + i + "]"+ "[" + j + "] = " + array[i * size + j] );
                }
        }
    }

    /**
     * Is the constructor that implements master-worker matrix transfers and
     * matrix multiplication.
     * @param size   the option to print out all matrices ( print if true )
     */
    public MatrixMultiplyer(int size) throws MPIException {
        myrank = MPI.COMM_WORLD.Rank( );
        nprocs = MPI.COMM_WORLD.Size( );

        printOption = true;
        if ( myrank == 0 ) {
            // I'm a master.

            // Construct message components.
            averows = size / nprocs;
            extra = size % nprocs;
            offset[0] = 0;
            mtype = tagFromMaster;

            // Start timer.
            Date startTime = new Date( );

            // Trasfer matrices to each worker.
            for ( int rank = 0; rank < nprocs; rank++ ) {
                rows[0] = ( rank < extra ) ? averows + 1 : averows;
                System.out.println( "sending " + rows[0] + " rows to rank " + rank );
                if ( rank != 0 ) {
                    MPI.COMM_WORLD.Send( offset, 0, 1, MPI.INT, rank, mtype );
                    MPI.COMM_WORLD.Send( rows, 0, 1, MPI.INT, rank, mtype );
                    MPI.COMM_WORLD.Send( a, offset[0] * size, rows[0] * size, MPI.DOUBLE, rank, mtype );
                    MPI.COMM_WORLD.Send( b, 0, size * size, MPI.DOUBLE, rank, mtype );
                }
                offset[0] += rows[0];
            }

            // Perform matrix multiplication.
            compute( size );

            // Collect results from each worker.
            int mytpe = tagFromWorker;
            for ( int source = 1; source < nprocs; source++ ) {
                MPI.COMM_WORLD.Recv( offset, 0, 1, MPI.INT, source, mtype );
                MPI.COMM_WORLD.Recv( rows, 0, 1, MPI.INT, source, mtype );
                MPI.COMM_WORLD.Recv( c, offset[0] * size, rows[0] * size, MPI.DOUBLE, source, mtype );
            }

            // Stop timer.
            Date endTime = new Date( );

            // Print out results
            System.out.println( "result c:" );
            print( c );

            System.out.println( "time elapsed = " + ( endTime.getTime( ) - startTime.getTime( ) ) + " msec" );
        }
        else {
            // I'm a worker.

            // Receive matrices.
            int mtype = tagFromMaster;
            MPI.COMM_WORLD.Recv( offset, 0, 1, MPI.INT, master, mtype );
            MPI.COMM_WORLD.Recv( rows, 0, 1, MPI.INT, master, mtype );
            MPI.COMM_WORLD.Recv( a, 0, rows[0] * size, MPI.DOUBLE, master,
                    mtype );
            MPI.COMM_WORLD.Recv( b, 0, size * size, MPI.DOUBLE, master,
                    mtype );

            // Perform matrix multiplication.
            compute( size );

            // Send results to the master.
            MPI.COMM_WORLD.Send( offset, 0, 1, MPI.INT, master, mtype );
            MPI.COMM_WORLD.Send( rows, 0, 1, MPI.INT, master, mtype );
            MPI.COMM_WORLD.Send( c, 0, rows[0] * size, MPI.DOUBLE, master,
                    mtype );
        }

        try {
            // Print out a complication message.
            InetAddress inetaddr = InetAddress.getLocalHost( );
            String ipname = inetaddr.getHostName( );
            System.out.println( "rank[" + myrank + "] at " + ipname +
                    ": multiplication completed" );
        } catch ( UnknownHostException e ) {
            System.err.println( e );
        }
    }

    private static  double[]  readFile(String path, Charset encoding) throws IOException
    {
        ArrayList<String> list = new ArrayList<>();
        File file = new File(path);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String text;

            while ((text = reader.readLine()) != null) {
                list.add(text);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }
        int size = list.size()*list.size();
        double[] vals = new double[size];
        int j = 0;
        ArrayList<Double> values = new ArrayList<Double>();
        for (String line : list) {
            String[] split = line.split(";");
            for(int i = 0; i < split.length; i++) {
                vals[j] = Double.valueOf(split[i]);
                j++;
            }
        }
        return vals;
    }

    /**
     *
     * @param args Receive the matrix size and the print option in args[0] and
     *             args[1]
     */
    public static void main( String[] args ) throws MPIException {
        // Check # args.

        // Start the MPI library.
        MPI.Init( args );

        // Will initialize size[0] with args[1] and option with args[2] (y | n)
       // int size[] = new int[1];
        //boolean option[] = new boolean[1];
       // option[0] =  false;

        // args[] are only available at rank 0. Don't check args[] at other
        // ranks

        // Broadcast size and option to all workers.
        //MPI.COMM_WORLD.Bcast( size, 0, 1, MPI.INT, master );
        //MPI.COMM_WORLD.Bcast( option, 0, 1, MPI.BOOLEAN, master );


        int size = getMatricesFromFiles();
        // Compute matrix multiplication in both master and workers.
        new MatrixMultiplyer(size);

        // Terminate the MPI library.
        MPI.Finalize( );
    }

    private static int  getMatricesFromFiles() {
        try {
            a = readFile("C:\\Users\\ctsch\\Documents\\Projects\\BigData\\aufgabe6openmpi\\aufgabe6openmpi\\src\\at\\fhv\\itm2018\\matrix\\Matrix3.txt", Charset.defaultCharset());
            b = readFile("C:\\Users\\ctsch\\Documents\\Projects\\BigData\\aufgabe6openmpi\\aufgabe6openmpi\\src\\at\\fhv\\itm2018\\matrix\\Matrix4.txt", Charset.defaultCharset());
            return ( int )Math.sqrt( ( double )a.length );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
