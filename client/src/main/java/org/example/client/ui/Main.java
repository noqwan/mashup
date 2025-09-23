package org.example.client.ui;

import org.example.client.service.ClientMovieService;
import org.example.client.service.ClientMovieServiceFactory;
import org.example.client.service.dto.ClientMovieDto;
import org.example.client.service.exceptions.ClientMovieNotRemovableException;
import org.example.client.service.exceptions.ClientSaleExpirationException;
import org.example.client.service.utils.exceptions.InputValidationException;
import org.example.client.service.utils.exceptions.InstanceNotFoundException;

import java.util.List;


public class Main {

    public static void main(String[] args) {

        if(args.length == 0) {
            printUsageAndExit();
        }
        ClientMovieService clientMovieService = ClientMovieServiceFactory.getService();
        if("-a".equalsIgnoreCase(args[0])) {
            validateArgs(args, 6, new int[] {2, 3, 5});

            // [add] MovieServiceClient -a <title> <hours> <minutes> <description> <price>

            try {
                Long movieId = clientMovieService.addMovie(new ClientMovieDto(null,
                        args[1], Short.valueOf(args[2]), Short.valueOf(args[3]),
                        args[4], Float.valueOf(args[5])));

                System.out.println("Movie " + movieId + " created sucessfully");

            } catch (NumberFormatException | InputValidationException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        } else if("-r".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {1});

            // [remove] MovieServiceClient -r <movieId>

            try {
                clientMovieService.removeMovie(Long.parseLong(args[1]));

                System.out.println("Movie with id " + args[1] +
                        " removed sucessfully");

            } catch (NumberFormatException | InstanceNotFoundException | ClientMovieNotRemovableException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        } else if("-u".equalsIgnoreCase(args[0])) {
            validateArgs(args, 7, new int[] {1, 3, 4, 6});

            // [update] MovieServiceClient -u <movieId> <title> <hours> <minutes> <description> <price>

            try {
                clientMovieService.updateMovie(new ClientMovieDto(
                        Long.valueOf(args[1]),
                        args[2], Short.valueOf(args[3]), Short.valueOf(args[4]),
                        args[5], Float.valueOf(args[6])));

                System.out.println("Movie " + args[1] + " updated sucessfully");

            } catch (NumberFormatException | InputValidationException |
                     InstanceNotFoundException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        } else if("-f".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {});

            // [find] MovieServiceClient -f <keywords>

            try {
                List<ClientMovieDto> movies = clientMovieService.findMovies(args[1]);
                System.out.println("Found " + movies.size() +
                        " movie(s) with keywords '" + args[1] + "'");
                for (int i = 0; i < movies.size(); i++) {
                    ClientMovieDto movieDto = movies.get(i);
                    System.out.println("Id: " + movieDto.getMovieId() +
                            ", Title: " + movieDto.getTitle() +
                            ", Runtime: " + (movieDto.getRuntimeHours() + " h - ") +
                            (movieDto.getRuntimeMinutes() + " m") +
                            ", Description: " + movieDto.getDescription() +
                            ", Price: " + movieDto.getPrice());
                }
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        } else {
            printUsageAndExit();
        }

    }

    public static void validateArgs(String[] args, int expectedArgs,
                                    int[] numericArguments) {
        if(expectedArgs != args.length) {
            printUsageAndExit();
        }
        for(int i = 0 ; i< numericArguments.length ; i++) {
            int position = numericArguments[i];
            try {
                Double.parseDouble(args[position]);
            } catch(NumberFormatException n) {
                printUsageAndExit();
            }
        }
    }

    public static void printUsageAndExit() {
        printUsage();
        System.exit(-1);
    }

    public static void printUsage() {
        System.err.println("Usage:\n" +
                "    [add]    MovieServiceClient -a <title> <hours> <minutes> <description> <price>\n" +
                "    [remove] MovieServiceClient -r <movieId>\n" +
                "    [update] MovieServiceClient -u <movieId> <title> <hours> <minutes> <description> <price>\n" +
                "    [find]   MovieServiceClient -f <keywords>");
    }

}