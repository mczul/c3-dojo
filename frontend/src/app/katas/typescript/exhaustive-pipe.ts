import { Pipe, PipeTransform } from '@angular/core';

export type Client = 'MVV' | 'WienIT' | 'GEMA' | 'Amprion';

@Pipe({
  name: 'c3Client',
})
export class C3ClientPipe implements PipeTransform {

  transform(value: Client): string {
    switch (value) {
      case 'MVV': return 'MVV Energie AG';
      case 'WienIT': return 'Digitaler Backbone der Wiener Stadtwerke-Gruppe';
      case 'GEMA': return 'Gesellschaft für musikalische Aufführungs- und mechanische Vervielfältigungsrechte';
      case 'Amprion': return 'Amprion GmbH'
      default:
        const exhaustiveCheck: never = value;
        return exhaustiveCheck;
    }
  }

}
