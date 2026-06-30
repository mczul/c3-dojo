import {ChangeDetectionStrategy, Component, OnDestroy, OnInit, signal} from '@angular/core';

@Component({
  selector: 'c3-sse-dummy',
  imports: [],
  template: `
    <h3>SSE :: Dummy</h3>
    @if (initialized()) {
      <p>
        {{ lastMessage() }}
      </p>
    } @else {
      ...
    }
  `,
  styles: ``,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SseDummy implements OnInit, OnDestroy {
  private eventSource = new EventSource('/api/sse/dummy', {withCredentials: true});

  protected readonly lastMessage = signal<string>('???');
  protected readonly initialized = signal<boolean>(false);

  ngOnInit(): void {
    this.eventSource.onopen = (event: Event) => {
      console.log(`Open: ${JSON.stringify(event)}`);
      this.initialized.set(true);
      this.lastMessage.set(`Open: ${JSON.stringify(event)}`);
    };

    this.eventSource.onmessage = (event: MessageEvent) => {
      console.log(`Received ${JSON.stringify(event.data)}`);
      this.lastMessage.set(`Message: ${event.data}`);
    };

    this.eventSource.onerror = (event: Event) => {
      console.warn(`Error: ${JSON.stringify(event)}`);
      this.lastMessage.set(`Error: ${JSON.stringify(event)}`)
    };
  }

  ngOnDestroy(): void {
    this.eventSource.close();
  }

}
