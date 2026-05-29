/**
 * @file lib/data.ts
 * @description Datos de ejemplo y tipos para FinanziApp
 * Este archivo contiene los tipos TypeScript y datos mock para la aplicación
 */

// ============================================
// TIPOS DE DATOS
// ============================================

/**
 * Representa una transacción financiera
 */
export interface Transaction {
  id: string;
  date: string;
  description: string;
  category: string;
  amount: number;
  type: 'income' | 'expense';
  account: string;
}

/**
 * Representa un punto de datos para el gráfico de flujo de efectivo
 */
export interface CashFlowData {
  day: string;
  income: number;
  expense: number;
}

/**
 * Representa una categoría de gastos para el gráfico circular
 */
export interface CategoryData {
  name: string;
  value: number;
  color: string;
}

/**
 * Representa una alerta del sistema
 */
export interface Alert {
  id: string;
  type: 'warning' | 'success' | 'info';
  message: string;
}

/**
 * Representa un elemento de navegación
 */
export interface NavItem {
  id: string;
  label: string;
  icon: string;
  href: string;
  active?: boolean;
}

// ============================================
// DATOS MOCK
// ============================================

/**
 * Transacciones recientes de ejemplo
 */
export const mockTransactions: Transaction[] = [
  {
    id: '1',
    date: '12/21/2023',
    description: 'Gasto en alimentación',
    category: 'Alimentación',
    amount: 13000,
    type: 'expense',
    account: 'Cuenta COP'
  },
  {
    id: '2',
    date: '02/04/2023',
    description: 'Control Financisto',
    category: 'Transporte',
    amount: 2300,
    type: 'expense',
    account: 'Cuenta COP'
  },
  {
    id: '3',
    date: '13/04/2023',
    description: 'Gasto en alimentación',
    category: 'Transporte',
    amount: 1000,
    type: 'expense',
    account: 'Cuenta COP'
  },
  {
    id: '4',
    date: '13/04/2023',
    description: 'Salario mensual',
    category: 'Salario',
    amount: 3500000,
    type: 'income',
    account: 'Cuenta COP'
  }
];

/**
 * Datos de flujo de efectivo mensual para el gráfico de líneas
 */
export const mockCashFlowData: CashFlowData[] = [
  { day: '00', income: 300, expense: 200 },
  { day: '02', income: 450, expense: 380 },
  { day: '05', income: 600, expense: 450 },
  { day: '09', income: 900, expense: 600 },
  { day: '13', income: 1200, expense: 800 },
  { day: '15', income: 1100, expense: 950 },
  { day: '18', income: 1350, expense: 1100 },
  { day: '21', income: 1200, expense: 950 },
  { day: '24', income: 1000, expense: 850 },
  { day: '26', income: 1150, expense: 900 },
  { day: '28', income: 1300, expense: 1050 },
  { day: '30', income: 1500, expense: 1200 }
];

/**
 * Datos de gastos por categoría para el gráfico circular
 */
export const mockCategoryData: CategoryData[] = [
  { name: 'Vivienda', value: 35, color: '#1e88e5' },
  { name: 'Alimentación', value: 25, color: '#43a047' },
  { name: 'Comida', value: 15, color: '#fb8c00' },
  { name: 'Transporte', value: 15, color: '#e53935' },
  { name: 'Otros', value: 10, color: '#8e24aa' }
];

/**
 * Alertas del sistema
 */
export const mockAlerts: Alert[] = [
  {
    id: '1',
    type: 'warning',
    message: 'Gasto en alimentación superado'
  },
  {
    id: '2',
    type: 'success',
    message: 'Meta de ahorro alcanzada 80%'
  }
];

/**
 * Elementos de navegación principal
 */
export const navItems: NavItem[] = [
  { id: 'home', label: 'Home', icon: 'home', href: '/', active: true },
  { id: 'cuentas', label: 'Cuentas', icon: 'wallet', href: '/cuentas' },
  { id: 'presupuestos', label: 'Presupuestos', icon: 'pie-chart', href: '/presupuestos' },
  { id: 'informes', label: 'Informes', icon: 'bar-chart', href: '/informes' },
  { id: 'educacion', label: 'Educación', icon: 'graduation-cap', href: '/educacion' },
  { id: 'ajustes', label: 'Ajustes', icon: 'settings', href: '/ajustes' }
];

/**
 * Elementos de navegación del sidebar (desktop)
 */
export const sidebarNavItems: NavItem[] = [
  { id: 'dashboard', label: 'Dashboard', icon: 'layout-dashboard', href: '/', active: true },
  { id: 'cuentas', label: 'Cuentas', icon: 'wallet', href: '/cuentas' },
  { id: 'presupuestos', label: 'Presupuestos', icon: 'pie-chart', href: '/presupuestos' },
  { id: 'informes', label: 'Informes', icon: 'bar-chart-2', href: '/informes' },
  { id: 'educacion', label: 'Educación Financiera', icon: 'graduation-cap', href: '/educacion' },
  { id: 'configuracion', label: 'Configuración', icon: 'settings', href: '/configuracion' }
];

/**
 * Categorías disponibles para transacciones
 */
export const categories = [
  'Alimentación',
  'Transporte',
  'Vivienda',
  'Salud',
  'Entretenimiento',
  'Educación',
  'Salario',
  'Inversiones',
  'Otros'
];

// ============================================
// FUNCIONES UTILITARIAS
// ============================================

/**
 * Formatea un número como moneda colombiana (COP)
 * @param amount - Cantidad a formatear
 * @returns String formateado como moneda
 */
export function formatCurrency(amount: number): string {
  return new Intl.NumberFormat('es-CO', {
    minimumFractionDigits: 0,
    maximumFractionDigits: 0
  }).format(amount);
}

/**
 * Obtiene el color asociado a una categoría
 * @param category - Nombre de la categoría
 * @returns Color en formato hexadecimal
 */
export function getCategoryColor(category: string): string {
  const colors: Record<string, string> = {
    'Alimentación': '#43a047',
    'Transporte': '#e53935',
    'Vivienda': '#1e88e5',
    'Salud': '#8e24aa',
    'Entretenimiento': '#fb8c00',
    'Educación': '#00acc1',
    'Salario': '#43a047',
    'Inversiones': '#1e88e5',
    'Otros': '#757575'
  };
  return colors[category] || '#757575';
}
