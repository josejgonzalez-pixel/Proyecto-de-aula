/**
 * @file app/page.tsx
 * @description Página principal del Dashboard de FinanziApp
 * 
 * Esta página implementa un diseño responsive:
 * - Mobile: Layout de una columna con navegación inferior
 * - Desktop: Layout de tres columnas con sidebar lateral
 * 
 * @component Dashboard
 */

import { Header, Sidebar, BottomNav } from '@/components/layout';
import {
  SummaryCards,
  AlertsCard,
  CashFlowChart,
  TransactionsTable,
  CategoryChart,
  QuickAddButton,
  QuickAddCard,
} from '@/components/dashboard';

/**
 * Componente principal de la página Dashboard
 * Organiza todos los widgets financieros en un layout responsive
 */
export default function Dashboard() {
  return (
    <div className="flex min-h-screen flex-col lg:flex-row">
      {/* Sidebar - Solo visible en desktop */}
      <Sidebar />
      
      {/* Contenido principal */}
      <div className="flex flex-1 flex-col">
        {/* Header */}
        <Header />
        
        {/* Contenido del dashboard */}
        <main className="flex-1 overflow-auto">
          <div className="mx-auto max-w-7xl p-4 pb-24 lg:p-6 lg:pb-6">
            {/* Título mobile */}
            <h1 className="mb-4 text-xl font-bold lg:hidden">Dashboard</h1>
            
            {/* Grid principal del dashboard */}
            <div className="grid gap-4 lg:grid-cols-3 lg:gap-6">
              {/* Columna principal (2/3 en desktop) */}
              <div className="space-y-4 lg:col-span-2 lg:space-y-6">
                {/* Tarjetas de resumen */}
                <SummaryCards />
                
                {/* Alertas - Solo visible en mobile */}
                <div className="lg:hidden">
                  <AlertsCard />
                </div>
                
                {/* Gráfico de flujo de efectivo */}
                <CashFlowChart />
                
                {/* Tabla de transacciones */}
                <TransactionsTable />
              </div>
              
              {/* Sidebar derecho (1/3 en desktop) */}
              <div className="hidden space-y-4 lg:block lg:space-y-6">
                {/* Alertas */}
                <AlertsCard />
                
                {/* Gráfico de categorías */}
                <CategoryChart />
                
                {/* Formulario de registro rápido */}
                <QuickAddCard />
              </div>
            </div>
          </div>
        </main>
        
        {/* Botón flotante Quick Add - Solo mobile */}
        <QuickAddButton />
        
        {/* Navegación inferior - Solo mobile */}
        <BottomNav />
      </div>
    </div>
  );
}
