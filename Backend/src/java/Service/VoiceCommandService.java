/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Model.Categoria;
import Model.Meta;
import Model.Presupuesto;
import Model.TipoComando;
import Model.VoiceCommandResponse;
import Util.Response;
import java.time.LocalDate;

/**
 *
 * @author Camil
 */
public class VoiceCommandService {
    private IntentDetector detector;
    private NLPService nlp;

    private CategoriaService categoriaService;
    private MetaService metaService;
    private PresupuestoService presupuestoService;
    private GastoService gastoService;
    private IngresoService ingresoService;

    public VoiceCommandService() {

        detector = new IntentDetector();
        nlp = new NLPService();

        categoriaService = new CategoriaService();
        metaService = new MetaService();
        presupuestoService = new PresupuestoService();
        gastoService = new GastoService();
        ingresoService = new IngresoService();
    }

    public VoiceCommandResponse procesar(String mensaje, int idUsuario) {

        try {

            mensaje = nlp.limpiarTexto(mensaje);

            TipoComando comando =
                    detector.detectar(mensaje);

            switch(comando){

                case CREAR_CATEGORIA:
                    return crearCategoria(mensaje);

                case CREAR_META:
                    return crearMeta(mensaje, idUsuario);

                case MOSTRAR_METAS:
                    return mostrarMetas(idUsuario);

                case MOSTRAR_GASTOS:
                    return mostrarGastos(idUsuario);

                case MOSTRAR_INGRESOS:
                    return mostrarIngresos();

                case CONSULTAR_PRESUPUESTO:
                    return consultarPresupuesto(idUsuario);

                default:
                    return new VoiceCommandResponse(
                            false,
                            "No entendí el comando."
                    );
            }

        } catch(Exception e){

            return new VoiceCommandResponse(
                    false,
                    e.getMessage()
            );
        }
    }
    
    private VoiceCommandResponse crearCategoria(String mensaje) {

        String nombre =
                extraerNombreCategoria(mensaje);

        Categoria categoria =
                new Categoria(
                        0,
                        nombre,
                        "GASTO"
                );

        Response<Categoria> r =
                categoriaService.insertar(categoria);

        return new VoiceCommandResponse(
                r.isEstado(),
                r.getMensaje()
        );
    }
    
    private String extraerNombreCategoria(String mensaje) {

        String texto = mensaje.toLowerCase();

        if (texto.contains("llamada")) {

            return mensaje.substring(
                    texto.indexOf("llamada") + 7
            ).trim();
        }

        return "Categoria Nueva";
    }
    
    private VoiceCommandResponse crearMeta(
        String mensaje,
        int idUsuario)
        throws Exception {

        Meta meta = new Meta();

        meta.setNombreMeta("Meta creada por voz");
        meta.setMontoMeta(1000000);
        meta.setMontoActual(0);
        meta.setFechaLimite(
                LocalDate.now().plusMonths(6));

        meta.setIdUsuario(idUsuario);

        Response<Meta> r =
                metaService.insertar(meta);

        return new VoiceCommandResponse(
                r.isEstado(),
                r.getMensaje()
        );
    }
    
    private VoiceCommandResponse mostrarMetas(
        int idUsuario)
        throws Exception {

        Response<Meta> r =
                metaService.obtenerTodos();

        StringBuilder sb =
                new StringBuilder();

        if (r.getLista() != null) {

            for (Meta m : r.getLista()) {

                if (m.getIdUsuario() == idUsuario) {

                    sb.append(m.getNombreMeta())
                      .append(" - ")
                      .append(m.getMontoMeta())
                      .append("\n");
                }
            }
        }

        return new VoiceCommandResponse(
                true,
                sb.toString()
        );
    }
    
    private VoiceCommandResponse mostrarGastos(int idUsuario)
        throws Exception{

            double total =
                    gastoService.calcularTotalGastos();

            return new VoiceCommandResponse(
                    true,
                    "Total gastos: $" + total
        );
    }
    
    private VoiceCommandResponse mostrarIngresos()
        throws Exception{

            double total =
                    ingresoService.calcularTotalIngresos();

            return new VoiceCommandResponse(
                    true,
                    "Total ingresos: $" + total
        );
    }
    
   private VoiceCommandResponse consultarPresupuesto(
        int idUsuario)
        throws Exception {

        Response<Presupuesto> r =
                presupuestoService.obtenerTodos();

        if (r.getLista() == null
                || r.getLista().isEmpty()) {

            return new VoiceCommandResponse(
                    false,
                    "No existen presupuestos registrados."
            );
        }

        StringBuilder sb =
                new StringBuilder();

        for (Presupuesto p : r.getLista()) {

            if (p.getIdUsuario() == idUsuario) {

                sb.append("Presupuesto ID: ")
                  .append(p.getIdPresupuesto())
                  .append("\n");

                sb.append("Monto Inicial: $")
                  .append(p.getMontoInicial())
                  .append("\n");

                sb.append("Monto Actual: $")
                  .append(p.getMontoActual())
                  .append("\n\n");
            }
        }

        return new VoiceCommandResponse(
                true,
                sb.toString()
        );
    }
}

