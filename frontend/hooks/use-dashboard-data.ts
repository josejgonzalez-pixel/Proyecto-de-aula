import { useState, useEffect } from 'react';

export const useDashboardData = () => {
  const [data, setData] = useState({ summary: null, categories: [], transactions: [] });

  useEffect(() => {
    const fetchData = async () => {
      try {
        // Llama a los endpoints que configuraste en tu TransaccionServlet
        const [sumRes, catRes, transRes] = await Promise.all([
          fetch('http://localhost:8080/FinanziApp/api/transacciones?accion=resumen'),
          fetch('http://localhost:8080/FinanziApp/api/transacciones?accion=gastosPorCategoria'),
          fetch('http://localhost:8080/FinanziApp/api/transacciones') // Lista completa
        ]);
        
        setData({
          summary: await sumRes.json(),
          categories: await catRes.json(),
          transactions: await transRes.json()
        });
      } catch (error) {
        console.error("Error al cargar datos:", error);
      }
    };
    fetchData();
  }, []);

  return data;
};