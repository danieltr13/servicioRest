/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personaServicio;

import dao.PersonaRepository;
import entidades.Persona;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author MSI GF63
 */
@Path("persona")
public class GenericResource {

    PersonaRepository repository;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
        this.repository = new PersonaRepository();
    }

    /**
     * Retrieves representation of an instance of apiCliente.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @Path("/all")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public ArrayList<Persona> getPersonas() {
        List<Persona> personas = repository.buscarTodas();
        if (!personas.isEmpty()) {
            return (ArrayList<Persona>) personas;
        }
        return null;
    }

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getPersona(@PathParam("id") String idPersona) {
        //TODO return proper representation object
        Persona persona = repository.buscarporID(new Long(idPersona));
        if (persona != null) {
            return Response.status(200).entity(persona).build();
        }
        return Response.status(404).build();
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put(Persona p) {
        if (repository.actualizar(p)) {
            return Response.status(200).entity("UpDate Person id: " + p.getId() + " name: " + p.getNombre() + ", lastName:" + p.getApellido()).build();
        }
        return Response.status(404).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(Persona p) {
        if (p.isComplete()) {
            if (repository.guardar(p)) {
                return Response.status(200).entity("addPersonis is called, name: " + p.getNombre() + ", lastName:" + p.getApellido()).build();
            }
            return Response.status(404).build();
        }
        //Error
        return Response.status(406).entity("La información está incompleta").build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(Persona p) {
        if (repository.eliminar(p.getId())) {
            return Response.status(200).entity("Has been deleted: "+p.getId()).build();
        }
        return Response.status(404).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") String idPersona) {
        //TODO return proper representation object
        
        if (repository.eliminar(new Long(idPersona))) {
            return Response.status(200).entity("Has been deleted: "+idPersona).build();
        }
        return Response.status(404).build();
    }
}
