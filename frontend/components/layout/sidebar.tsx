/**
 * @file components/layout/sidebar.tsx
 * @description Sidebar de navegación para vista desktop
 * Incluye el logo y los enlaces de navegación principales
 */

'use client';

import Link from 'next/link';
import { usePathname } from 'next/navigation';
import {
  LayoutDashboard,
  Wallet,
  PieChart,
  Target,
  BarChart2,
  GraduationCap,
  Settings,
} from 'lucide-react';
import { cn } from '@/lib/utils';
import { Logo } from './header';

/**
 * Mapeo de iconos para los elementos de navegación
 */
const iconMap: Record<string, React.ComponentType<{ className?: string }>> = {
  'layout-dashboard': LayoutDashboard,
  'wallet': Wallet,
  'pie-chart': PieChart,
  'target-2': Target,
  'bar-chart-2': BarChart2,
  'graduation-cap': GraduationCap,
  'settings': Settings,
};

/**
 * Elementos de navegación del sidebar
 */
const sidebarItems = [
  { id: 'dashboard', label: 'Dashboard', icon: 'layout-dashboard', href: '/' },
  { id: 'cuentas', label: 'Cuentas', icon: 'wallet', href: '/cuentas' },
  { id: 'presupuestos', label: 'Presupuestos', icon: 'pie-chart', href: '/presupuestos' },
  { id: 'metas', label: 'Metas', icon: 'target-2', href: '/metas' },
  { id: 'informes', label: 'Informes', icon: 'bar-chart-2', href: '/informes' },
  { id: 'educacion', label: 'Educación Financiera', icon: 'graduation-cap', href: '/educacion' },
];

/**
 * Componente Sidebar
 * Muestra la navegación principal en desktop con estado activo
 */
export function Sidebar() {
  const pathname = usePathname();
  
  return (
    <aside className="hidden lg:flex lg:w-64 lg:flex-col lg:border-r lg:bg-card h-screen">

      {/* Logo */}
      <div className="flex h-16 items-center border-b px-6 shink-0">
        <Logo />
      </div>
      
      <div className="grid grid-rows-[1fr_auto] flex-1 min-h-0">

      {/* Navegación */}
      <nav className="space-y-1 p-4 overflow-y-auto">
        {sidebarItems.map((item) => {
          const Icon = iconMap[item.icon];
          const isActive = pathname === item.href;
          
          return (
            <Link
              key={item.id}
              href={item.href}
              className={cn(
                'flex items-center gap-3 rounded-lg px-3 py-2.5 text-sm font-medium transition-colors',
                isActive
                  ? 'bg-primary/10 text-primary'
                  : 'text-muted-foreground hover:bg-muted hover:text-foreground'
              )}
            >
              {Icon && <Icon className="h-5 w-5" />}
              {item.label}
            </Link>
          );
        })}
      </nav>
      
      {/* Configuración al final */}
      <div className="border-t p-4 bg-card shrink-0">
        <Link
          href="/configuracion"
          className={cn(
            'flex items-center gap-3 rounded-lg px-3 py-2.5 text-sm font-medium transition-colors',
            pathname === '/configuracion'
              ? 'bg-primary/10 text-primary'
              : 'text-muted-foreground hover:bg-muted hover:text-foreground'
          )}
        >
          <Settings className="h-5 w-5" />
          Configuración
        </Link>
      </div>

      </div>
    </aside>
  );
}
