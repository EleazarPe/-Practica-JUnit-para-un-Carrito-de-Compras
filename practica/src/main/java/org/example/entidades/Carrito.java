package org.example.entidades;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.ArrayList;

public class Carrito {

    private static Carrito instancia;

    private ArrayList<ItemCarrito> itemCarritos;

    private Carrito() {
        this.itemCarritos = new ArrayList<>();
    }

    public static Carrito getInstance() {
        if (instancia == null) {
            instancia = new Carrito();
        }
        return instancia;
    }

    public void agregarItem(ItemCarrito item) {
        if (item == null) {
            return;
        }
        itemCarritos.add(item);
    }

    public ArrayList<ItemCarrito> getItems() {
        return itemCarritos;
    }

    public void elimarItem(Long itemId) {
        if (itemId == null) {
            return;
        }
        itemCarritos.removeIf(item -> item.getId().equals(itemId));
    }

    public boolean aumentarCantidadItem(Long id, int aumento) {
        boolean res = false;
        if (id == null || aumento <= 0) {
            return res;
        }

        for (ItemCarrito item : itemCarritos) {
            if (item.getId().equals(id)) {
                item.setCantidad(item.getCantidad() + aumento);
                return true;
            }
        }
        return res;
    }

    public boolean disminuirCantidadItem(Long id, int decremento) {
        boolean res = false;
        for (ItemCarrito item : itemCarritos) {
            if (item.getId().equals(id)) {
                if (item.getCantidad() > 1) {
                    item.setCantidad(item.getCantidad() - decremento);
                    return true;
                }
            }
        }
        return res;
    }


    public boolean asignarCantidad(Long id, int cantidad) {
        boolean res = false;
        if (id == null || cantidad <= 0) {
            return res;
        }

        for (ItemCarrito item : itemCarritos) {
            if (item.getId().equals(id)) {
                item.setCantidad(cantidad);
                return true;
            }
        }
        return res;
    }

    public BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        if (itemCarritos.isEmpty()) {
            return total;
        }
        for (ItemCarrito item : itemCarritos) {
            BigDecimal precio = item.getProducto().getPrecio();
            total = total.add(precio.multiply(BigDecimal.valueOf(item.getCantidad())));
        }
        return total;
    }
}

