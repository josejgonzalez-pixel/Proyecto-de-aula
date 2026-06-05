/**
 * @file components/layout/header.tsx
 * @description Header con menú móvil sincronizado con la navegación principal
 */

'use client';

import Link from 'next/link';
import { usePathname } from 'next/navigation';
import { 
  Sheet, 
  SheetContent, 
  SheetTrigger 
} from '../ui/sheet';
import { Button } from '../ui/button';
import { 
  Menu, 
  LayoutDashboard, 
  Wallet, 
  PieChart, 
  Target, 
  BarChart2, 
  Tag 
} from 'lucide-react';
import { cn } from '../../lib/utils';
import Image from 'next/image';
import logoSrc from '@/public/logo_finanziapp.png';

export const Logo = () => (
  <div className="flex items-center gap-2 font-bold text-xl text-primary">
    <Image
      src={logoSrc} 
      alt="FinanziApp Logo" 
      width={32} 
      height={32} 
      className="h-8 w-8" 
    />
    FinanziApp
  </div>
);

/**
 * Definimos los items. 
 * Asegúrate de que los 'href' coincidan exactamente con tu sidebar.tsx
 */
const navItems = [
  { id: 'dashboard', label: 'Dashboard', icon: LayoutDashboard, href: '/' },
  //{ id: 'cuentas', label: 'Cuentas', icon: Wallet, href: '/cuentas' },
  { id: 'presupuestos', label: 'Presupuestos', icon: PieChart, href: '/presupuestos' },
  { id: 'metas', label: 'Metas', icon: Target, href: '/metas' },
  { id: 'informes', label: 'Informes', icon: BarChart2, href: '/informes' },
  { id: 'categorias', label: 'Categorías', icon: Tag, href: '/categorias' },
];

export function Header() {
  const pathname = usePathname();

  return (
    <header className="sticky top-0 z-50 w-full border-b bg-background/95 backdrop-blur">
      <div className="flex h-16 items-center px-4">
        
        {/* Logo visible solo en móvil */}
        <div className="lg:hidden">
          <Logo />
        </div>

        {/* Menú desplegable móvil */}
        <Sheet>
          <SheetTrigger asChild>
            <Button variant="ghost" className="lg:hidden ml-auto">
              <Menu className="h-6 w-6" />
            </Button>
          </SheetTrigger>
          <SheetContent side="left" className="w-64">
            <div className="flex flex-col gap-6 py-6">
              <Logo />
              <nav className="flex flex-col gap-2">
                {navItems.map((item) => {
                  const Icon = item.icon;
                  // Si pathname es exactamente igual al href, se marca como activo
                  const isActive = pathname === item.href; 
                  
                  return (
                    <Link
                      key={item.id}
                      href={item.href}
                      className={cn(
                        "flex items-center gap-3 px-3 py-2 rounded-lg text-sm font-medium transition-colors",
                        isActive 
                          ? "bg-primary/10 text-primary" 
                          : "text-muted-foreground hover:bg-muted"
                      )}
                    >
                      <Icon className="h-5 w-5" />
                      {item.label}
                    </Link>
                  );
                })}
              </nav>
            </div>
          </SheetContent>
        </Sheet>
      </div>
    </header>
  );
}