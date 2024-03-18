import java.io.Serializable;
import java.util.ArrayList;

public class ParticlesAndConstraintsSet implements Serializable {
    private ArrayList<Particle> particles = new ArrayList<>();
    private ArrayList<Constraint> constraints = new ArrayList<>();


    public void addParticle(Particle particle){
        this.particles.add(particle);
    }

    public ArrayList<Particle> getParticles(){
        return this.particles;
    }


    public void addConstraint(Constraint constraint){
        this.constraints.add(constraint);
    }

    public ArrayList<Constraint> getConstraints(){
        return this.constraints;
    }
}
