package edu.eci.arsw.blueprints;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Set;

public class BlueprintsMain {

    public static void main(String[] args) throws BlueprintNotFoundException {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        BlueprintsServices services = context.getBean(BlueprintsServices.class);

        Blueprint bp1 = new Blueprint("isa", "plano1", new Point[]{new Point(1, 1), new Point(2, 2)});
        Blueprint bp2 = new Blueprint("isa", "plano2", new Point[]{new Point(3, 4), new Point(4, 4)});
        Blueprint bp3 = new Blueprint("nicolas", "plano3", new Point[]{new Point(7, 25), new Point(25, 7)});
        Blueprint bp4 = new Blueprint("nicolas", "plano4", new Point[]{new Point(1, 1), new Point(1, 1), new Point(2, 2), new Point(2, 2)});
        Blueprint bp5 = new Blueprint("nicolas", "plano5", new Point[]{new Point( 0, 0), new Point(1, 1), new Point(2, 2), new Point(3, 3)});


        services.addNewBlueprint(bp1);
        services.addNewBlueprint(bp2);
        services.addNewBlueprint(bp3);
        services.addNewBlueprint(bp4);
        services.addNewBlueprint(bp5);

        System.out.println("Todos los planos");
        Set<Blueprint> all = services.getAllBlueprints();
        all.forEach(System.out::println);

        System.out.println("Planos Isa");
        Set<Blueprint> isaPlans = services.getBlueprintsByAuthor("isa");
        isaPlans.forEach(System.out::println);

        System.out.println("Plano especifico");
        Blueprint found = services.getBlueprint("nicolas", "plano3");
        System.out.println(found);

        // Prueba de RedundancyFilter
        System.out.println("Plano probando RedundancyFilter");
        Blueprint RFiltered = services.getBlueprint("nicolas", "plano4");

        System.out.println(RFiltered.getAuthor() + " - " + RFiltered.getName());

        for (Point point : RFiltered.getPoints()) {
            System.out.println("(" + point.getX() + ", " + point.getY() + ")");
        }

        // Prueba de SubsamplingFilter
        System.out.println("Plano probando SubsamplingFilter");
        Blueprint subFiltered = services.getBlueprint("nicolas", "plano5");

        System.out.println(subFiltered.getAuthor() + " - " + subFiltered.getName());


        for (Point point : subFiltered.getPoints()) {
            System.out.println("(" + point.getX() + ", " + point.getY() + ")");
        }
    }

}
