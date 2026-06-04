// hooks/use-dashboard-data.ts
import { useState, useEffect } from 'react';

export function useDashboardData() {
  const [data, setData] = useState({ ingresos: 0, gastos: 0, saldo: 0 });
  const [loading, setLoading] = useState(true);
  const URL = 'http://localhost:8080/FinanziApp/api/transacciones?accion=resumen';

  useEffect(() => {
    console.log(data);
    fetch(URL)
      .then(res => res.json())
      .then(json => {
        console.log("Datos recibidos del servidor:", json);
        setData(json);
        setLoading(false);
      })
      .catch(err => {
        console.error("Error cargando dashboard:", err);
        setLoading(false);
      });
  }, []);

  return { ...data, loading };
}