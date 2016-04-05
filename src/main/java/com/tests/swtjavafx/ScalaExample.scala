package com.tests.swtjavafx


import javafx.embed.swt.FXCanvas
import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.{Group, Scene}
import javafx.scene.control.Button
import javafx.scene.paint.Color

import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.layout.RowLayout
import org.eclipse.swt.widgets.{Display, Event, Listener, Shell, Button => SwtButton}

object ScalaExample {
  def main(args: Array[String]) {
    val display: Display = new Display
    val shell: Shell = new Shell(display)
    val layout: RowLayout = new RowLayout
    shell.setLayout(layout)
    val swtButton: SwtButton = new SwtButton(shell, SWT.PUSH)
    swtButton.setText("SWT Button")
    val fxCanvas: FXCanvas = new FXCanvas(shell, SWT.NONE) {
      override def computeSize(wHint: Int, hHint: Int, changed: Boolean): Point = {
        getScene.getWindow.sizeToScene()
        val width: Int = getScene.getWidth.toInt
        val height: Int = getScene.getHeight.toInt
        new Point(width, height)
      }
    }
    val group: Group = new Group
    val jfxButton: Button = new Button("JFX Button")
    jfxButton.setId("ipad-dark-grey")
    group.getChildren.add(jfxButton)
    val scene: Scene = new Scene(
      group, Color.rgb(
        shell.getBackground.getRed,
        shell.getBackground.getGreen,
        shell.getBackground.getBlue
      )
    )
    fxCanvas.setScene(scene)
    swtButton.addListener(SWT.Selection, new Listener() {
      def handleEvent(event: Event) {
        jfxButton.setText("JFX Button: Hello from SWT")
        shell.layout()
      }
    })
    jfxButton.setOnAction(new EventHandler[ActionEvent]() {
      def handle(event: ActionEvent) {
        swtButton.setText("SWT Button: Hello from JFX")
        shell.layout()
      }
    })
    shell.open()
    while (!shell.isDisposed) {
      {
        if (!display.readAndDispatch) {
          display.sleep
        }
      }
    }
    display.dispose()
  }
}